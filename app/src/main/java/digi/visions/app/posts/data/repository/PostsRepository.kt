package digi.visions.app.posts.data.repository

import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.scopes.ViewModelScoped
import digi.visions.app.posts.domain.repository.IPostsRepository
import digi.visions.app.remote.DataManager
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@BindType
@ViewModelScoped
class PostsRepository @Inject constructor(private val dataManager: DataManager) : IPostsRepository {
    override fun getPosts() = dataManager.getProducts().subscribeOn(Schedulers.io())
}