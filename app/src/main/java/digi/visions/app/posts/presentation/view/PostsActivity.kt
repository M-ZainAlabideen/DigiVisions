package digi.visions.app.posts.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import digi.visions.app.databinding.ActivityPostsBinding
import digi.visions.app.postDetails.presentation.view.PostDetailsActivity
import digi.visions.app.posts.presentation.adapter.PostsAdapter
import digi.visions.app.posts.presentation.viewModel.PostsViewModel
import digi.visions.app.shared.Post
import digi.visions.app.utils.Constants

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsBinding
    private val postsViewModel: PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initRecyclerView()
        observeResults()
        observeLoadingProgress()
        observeFailure()
        postsViewModel.onCreate()

    }

    private fun initRecyclerView() {
        binding.postsRv.apply {
            layoutManager = LinearLayoutManager(this@PostsActivity)
            adapter = PostsAdapter(ArrayList(), ::onItemClick)
        }
    }

    private fun observeResults() {
        postsViewModel.getPostsResult().observe(this) {
            getResultsAdapter()?.updateData(it)
        }
    }


    private fun observeLoadingProgress() {
        postsViewModel.progress.observe(this) { showProgress ->
            if (showProgress) {
                binding.postsRv.showShimmer()
            } else {
                binding.postsRv.hideShimmer()
            }
        }
    }

    private fun getResultsAdapter() =
        binding.postsRv.adapter as? PostsAdapter


    private fun onItemClick(post: Post) {
        val intent =
            Intent(this@PostsActivity, PostDetailsActivity::class.java)
        intent.putExtra(Constants.POST_ITEM, post)
        startActivity(intent)
    }

    private fun observeFailure() {
        postsViewModel.failure.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

}