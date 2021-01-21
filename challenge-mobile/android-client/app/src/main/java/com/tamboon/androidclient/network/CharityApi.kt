package com.tamboon.androidclient.network

import io.reactivex.Single
import retrofit2.http.GET

interface CharityApi {

    @GET("charities")
    fun getCharities(): Single<CharityResponse>
}