package ua.kpi.comsys.ip8313.ui.pictures

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PicturesApi {
    @GET("/api/")
    suspend fun getPictures(
        @Query("key") key: String = "19193969-87191e5db266905fe8936d565",
        @Query("q") q: String,
        @Query("image_type") imageType: String = "photo",
        @Query("per_page") count: Int = 27
    ): PictureSearchResults
}

fun getPicturesApi(): PicturesApi {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .callTimeout(1, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/" )
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(PicturesApi::class.java)
}