package digi.visions.app.shared

import com.google.gson.annotations.SerializedName

data class GetPosts(
    @SerializedName("results")
    val posts: List<Post>
)
