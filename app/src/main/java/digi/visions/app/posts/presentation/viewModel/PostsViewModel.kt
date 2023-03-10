package digi.visions.app.posts.presentation.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import digi.visions.app.posts.domain.repository.IPostsRepository
import digi.visions.app.room.AppDatabase
import digi.visions.app.room.entities.PostEntity
import digi.visions.app.shared.GetPosts
import digi.visions.app.shared.Post
import digi.visions.app.utils.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private var repository: IPostsRepository,
    application: Application
) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    val failure = MutableLiveData<String>()
    val progress = MutableLiveData(false)

    private val _results = SingleLiveEvent<List<Post>>()
    private val results: LiveData<List<Post>> get() = _results
    fun getPostsResult(): LiveData<List<Post>> {
        return results
    }

    private val compositeDisposable = CompositeDisposable()

    fun onCreate() {
        checkLocallyPosts()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.apply {
            if (!isDisposed) {
                dispose()
            }
        }
    }

    private fun getPosts() {
        progress.value = true
        compositeDisposable.add(
            repository.getPosts().observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleResults, ::handleError)
        )
    }


    private fun handleResults(response: GetPosts) {
        progress.value = false
        if (response.posts.isNotEmpty()) {
            _results.value = response.posts
            insertPost(PostEntity.setPostEntity(_results.value!!))
        }
    }

    private fun handleError(throwable: Throwable) {
        failure.value = throwable.message
        progress.value = false
    }

    @SuppressLint("CheckResult")
    private fun checkLocallyPosts() {
        Observable.fromCallable {
            AppDatabase.getDatabase(context = context)?.postsDao()!!.getAllPosts()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    showPostsLocally(it)
                } else
                    getPosts()
            }

    }

    private fun showPostsLocally(list: List<PostEntity>) {
        val posts: ArrayList<Post> = ArrayList()
        for (item in list)
            posts.addAll(item.posts)
        _results.value = posts
    }

    @SuppressLint("CheckResult")
    private fun insertPost(postEntity: PostEntity) {
        Observable.fromCallable {
            AppDatabase.getDatabase(context)?.postsDao()!!
                .insertPosts(postEntity)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}