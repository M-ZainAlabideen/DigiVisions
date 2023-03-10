package digi.visions.app.remote

import digi.visions.app.shared.GetPosts
import digi.visions.app.utils.NetworkUtils
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET(NetworkUtils.POSTS)
    fun getProducts(): Single<GetPosts>
}