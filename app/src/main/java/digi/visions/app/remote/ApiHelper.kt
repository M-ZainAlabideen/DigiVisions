package digi.visions.app.remote

import com.google.gson.GsonBuilder
import digi.visions.app.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiHelper {
    companion object {
        fun <T> getAPI(baseUrl: String, apiClass: Class<T>): T {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(getKeyInterceptor())
            okHttpClient.addInterceptor(loggingInterceptor)
            okHttpClient.readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            okHttpClient.connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            val gson = GsonBuilder().create()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(apiClass)
        }

        fun getKeyInterceptor(): Interceptor {
            return Interceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                val newRequest =
                    originalRequest.newBuilder().header(Constants.KEY, Constants.VALUE)
                        .build()
                chain.proceed(newRequest)
            }
        }
    }
}