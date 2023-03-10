package digi.visions.app.posts.presentation.viewModel

import android.app.Application
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import digi.visions.app.posts.domain.repository.IPostsRepository
import digi.visions.app.shared.GetPosts
import digi.visions.app.shared.Post
import io.reactivex.Single
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4ClassRunner::class)
class PostsViewModelTest {

    @Mock
    private lateinit var repository: IPostsRepository

    private lateinit var viewModel: PostsViewModel

    private val context: Application = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        viewModel = PostsViewModel(repository, context)
    }

    @Mock
    private lateinit var dataObserver: Observer<List<Post>>

    @Test
    fun fetchMyData_success() {
        val myData = listOf(Post("zxcv","item1", "10"), Post("asdf","item2", "20"))
        val observer = dataObserver
        viewModel.getPostsResult().observeForever(observer)
        Mockito.`when`(repository.getPosts()).thenReturn(Single.just(GetPosts(myData)))
        viewModel.onCreate()
        verify(observer).onChanged(myData)
    }

    @Mock
    private lateinit var errorObserver: Observer<String>

    @Test
    fun fetchMyData_failure() {
        val error = Exception("Error fetching data")
        val observer = errorObserver
        viewModel.failure.observeForever(observer)
        Mockito.`when`(repository.getPosts()).thenReturn(Single.error(error))
        viewModel.onCreate()
        verify(observer).onChanged(error.message!!)
    }
}