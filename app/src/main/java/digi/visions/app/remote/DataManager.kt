package digi.visions.app.remote

import digi.visions.app.shared.GetPosts
import digi.visions.app.utils.NetworkUtils
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor() {
    private var apiInterface: ApiInterface =
        ApiHelper.getAPI(NetworkUtils.BASE_URL, ApiInterface::class.java)

    fun getProducts(): Single<GetPosts> {
        return apiInterface.getProducts()
    }

}