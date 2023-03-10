package digi.visions.app.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import digi.visions.app.shared.Post


@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val posts: List<Post>
) {
    companion object {
        fun setPostEntity(
            posts: List<Post>
        ): PostEntity {
            return PostEntity(
                posts = posts
            )
        }
    }
}
