package com.example.artinstitutechicago_android.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PictureService {
    @GET("artworks")
    suspend fun getAllPictures(): PictureResponse

    @GET("artworks/{id}")
    suspend fun getPictureId(@Path("id") id: Int): Picture

    companion object{
        fun getInstace() : PictureService {
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.artic.edu/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PictureService::class.java)
        }
    }
}

