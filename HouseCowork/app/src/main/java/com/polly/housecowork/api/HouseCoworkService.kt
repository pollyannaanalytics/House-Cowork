package com.polly.housecowork.api

import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

interface HouseCoworkService {

    suspend fun test(): Response

    companion object{
        const val BASE_URL = "https://api.housecowork.com"
        fun create(): HouseCoworkService {

            val client = OkHttpClient
                .Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(HouseCoworkService::class.java)
        }
    }
}