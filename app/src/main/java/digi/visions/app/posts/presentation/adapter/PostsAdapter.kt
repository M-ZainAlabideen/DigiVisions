package digi.visions.app.posts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import digi.visions.app.databinding.ItemPostBinding
import digi.visions.app.shared.Post
import kotlin.properties.Delegates

class PostsAdapter(list: List<Post> = emptyList(),
                   val onItemClick: (Post) -> Unit
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var list: List<Post> by Delegates.observable(list) { _, old, new ->
        DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize() = old.size

                override fun getNewListSize() = new.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition].price == new[newItemPosition].price

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition] == new[newItemPosition]

            }
        ).also { result ->
            result.dispatchUpdatesTo(this@PostsAdapter)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Post>) {
        list = newList
    }

    class ViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: Post,
            onItemClick: (Post) -> Unit
        ) {
            binding.apply {
                name.text = product.name
                price.text = product.price
                container.setOnClickListener { onItemClick(product) }
            }

        }
    }
}
