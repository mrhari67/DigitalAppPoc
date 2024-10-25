package com.poc.speedreporter.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {

    companion object {
        private const val BASE_URL : String = "http://10.53.193.25:8080"
        private var apiService : APIService? = null
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

        private val httpClient: OkHttpClient.Builder =
            OkHttpClient.Builder().addInterceptor(logging)

        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())

        private val retrofit: Retrofit = retrofitBuilder.build()

        fun getInstance(): APIService {
            if(apiService == null)
                apiService = retrofit.create(APIService::class.java)

            return apiService!!
        }
    }
}