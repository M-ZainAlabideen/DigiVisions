package digi.visions.app.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import digi.visions.app.shared.Post
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromPost(posts: List<Post>?): String? {
        if (posts == null || posts.isEmpty()) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Post>>() {}.type
        return gson.toJson(posts, type)
    }

    @TypeConverter
    fun toPost(posts: String?): List<Post>? {
        if (posts == null || posts.isEmpty()) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Post>>() {}.type
        return gson.fromJson<List<Post>>(posts, type)
    }
}