package digi.visions.app.posts.domain.repository

import digi.visions.app.shared.GetPosts
import io.reactivex.Single

interface IPostsRepository {
    fun getPosts(): Single<GetPosts>
}