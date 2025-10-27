package com.example.artinstitutechicago_android.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PictureService {
    @GET("artworks")
    suspend fun getAllPictures(): PictureListResponse

    @GET("artworks")
    suspend fun getPageAllPictures(@Query("page") pages: Int, @Query ("limit") limit: Int) : PictureListResponse

    @GET("artworks/{id}")
    suspend fun getPictureId(@Path("id") id: Int): PictureResponse

    companion object{
        fun getInstace() : PictureService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.artic.edu/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PictureService::class.java)
        }
    }
}

