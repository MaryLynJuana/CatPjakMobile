package ua.kpi.comsys.ip8313.ui.books

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface BookApi {
    @GET("1.0/search/{req}")
    suspend fun getSearchedBooks(@Path("req") req: String): BookSearchResults
    @GET("1.0/books/{id}")
    suspend fun getBookData(@Path("id") id: String): Book
}

fun getBookApi(): BookApi {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.itbook.store/" )
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(BookApi::class.java)
}