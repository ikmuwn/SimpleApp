package kim.uno.simpleapp.data.remote

import kim.uno.simpleapp.data.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Retrofit @Inject constructor() {

    private val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            addInterceptor(logger)
            addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK 45076a78d2c22c9b409bd3be120f1d7e")
                    .build()

                it.proceed(request)
            }
        }.build()
    }

    private val logger by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    open suspend fun <T> proceed(suspend: suspend () -> Response<T>): Result<T> {
        return try {
            val response = suspend.invoke()
            if (response.isSuccessful)
                Result.Success(data = response.body())
            else
                throw RuntimeException(response.message())
        } catch (e: Throwable) {
            Result.Error(throwable = e)
        }
    }

}