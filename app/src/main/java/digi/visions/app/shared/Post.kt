package digi.visions.app.shared

import java.io.Serializable

data class Post(
    val uid: String,
    val name: String,
    val price: String
) : Serializable
