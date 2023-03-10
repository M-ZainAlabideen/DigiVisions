package digi.visions.app.posts.presentation.adapter

import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import digi.visions.app.shared.Post
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4ClassRunner::class)
class PostsAdapterTest {

    private lateinit var adapter: PostsAdapter
    private val itemList = listOf(Post("zxcv","item1", "10"), Post("asff","item2", "20"))

    @Before
    fun setUp() {
        adapter = mock(PostsAdapter::class.java)
    }
    private fun onItemClick(post: Post) {

    }

    @Test
    fun testOnItemClick() {
        val mockView = mock(View::class.java)
        adapter.onItemClick(itemList[0])
        verify(mockView).performClick()
    }

    @Test
    fun itemCount_isCorrect() {
        assertThat(adapter.itemCount, `is`(3))
    }

    @Test
    fun viewHolder_displaysCorrectData() {
        val adapter = PostsAdapter(itemList,::onItemClick)
        val parent = FrameLayout(ApplicationProvider.getApplicationContext())
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        adapter.onBindViewHolder(viewHolder, 1)
        assertThat(viewHolder.itemView, `is`("Item 2"))
    }
}