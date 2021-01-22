package com.tamboon.androidclient.network

import com.google.gson.JsonObject
import com.tamboon.androidclient.view.charityList.CharityResponse
import com.tamboon.androidclient.view.donation.DonationResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkApi {

    @GET("charities")
    fun getCharities(): Single<CharityResponse>

    @POST("donations")
    fun postDonation(@Body bean: String): Single<DonationResponse>
}