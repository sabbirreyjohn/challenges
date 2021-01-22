package com.tamboon.androidclient.view.donation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.omise.android.api.Client
import co.omise.android.api.RequestListener
import co.omise.android.models.CardParam
import co.omise.android.models.Token
import com.google.gson.Gson
import com.tamboon.androidclient.BuildConfig
import com.tamboon.androidclient.model.Payment
import com.tamboon.androidclient.model.ResponseType
import com.tamboon.androidclient.network.NetworkApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DonationRepository {

    fun sendDonationToOmise(payment: Payment): MutableLiveData<ResponseType> {
        var ldResponse = MutableLiveData<ResponseType>()
        val client = Client(BuildConfig.omise_public_key)
        val cardParam = CardParam(
            name = payment.name,
            number = payment.cardNumber,
            expirationMonth = payment.expMonth,
            expirationYear = payment.expYear,
            securityCode = payment.securityCode
        )
        val request = Token.CreateTokenRequestBuilder(cardParam).build()
        client.send(request, object : RequestListener<Token> {
            override fun onRequestSucceed(model: Token) {
                Log.i("token", model.toString())
                ldResponse.value = ResponseType(true, model.id)
            }

            override fun onRequestFailed(throwable: Throwable) {
                Log.i("token", throwable.message)
                ldResponse.value = ResponseType(false, throwable.message)
            }
        })
        return ldResponse
    }

    fun postDonationToServer(
        networkApi: NetworkApi,
        donation: Donation
    ): MutableLiveData<ResponseType> {
        var ldResponseType = MutableLiveData<ResponseType>()
        var gson = Gson()
        val disposable = networkApi.postDonation(gson.toJson(donation)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                ldResponseType.value = ResponseType(it.success, it.error_message)

            }, {
                ldResponseType.value = ResponseType(false, it.message)

            })
        return ldResponseType
    }
}