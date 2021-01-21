package com.tamboon.androidclient.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    companion object {
        lateinit var retrofit: Retrofit
        const val URL_BASE = "https://virtserver.swaggerhub.com/chakritw/tamboon-api/1.0.0/"
        fun getInstance(): Retrofit {
            if (!this::retrofit.isInitialized) {
                retrofit = Retrofit.Builder().baseUrl(URL_BASE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}