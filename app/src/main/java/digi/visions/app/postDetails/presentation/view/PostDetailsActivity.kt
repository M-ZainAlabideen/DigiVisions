package digi.visions.app.postDetails.presentation.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import digi.visions.app.R
import digi.visions.app.databinding.ActivityPostDetailsBinding
import digi.visions.app.shared.Post
import digi.visions.app.utils.Constants

@AndroidEntryPoint
class PostDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        setupToolbar()
        receiveBundle()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun receiveBundle() {
        val extras = intent.extras
        if (extras != null) {
            val post = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getSerializable(Constants.POST_ITEM, Post::class.java)
            } else {
                @Suppress("DEPRECATION") extras.getSerializable(Constants.POST_ITEM)
            }
            setData(post as Post)
        }
    }

    private fun setData(post: Post) {
        binding.apply {
            name.text = post.name
            price.text = post.price
        }
    }

}