package digi.visions.app.room.daos

import androidx.room.*
import digi.visions.app.room.entities.PostEntity

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(postEntity: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAllPosts() : List<PostEntity>
}