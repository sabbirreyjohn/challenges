package com.tamboon.androidclient.view.donation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.omise.android.api.Client
import co.omise.android.api.RequestListener
import co.omise.android.models.CardParam
import co.omise.android.models.Token
import com.tamboon.androidclient.BuildConfig
import com.tamboon.androidclient.model.Payment

object DonationRepository {

    fun sendDonationToOmise(payment: Payment): MutableLiveData<String> {
        var ldToken = MutableLiveData<String>()
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
                ldToken.value = model.id
            }

            override fun onRequestFailed(throwable: Throwable) {
                Log.i("token", throwable.message)
                ldToken.value = throwable.message
            }
        })
        return ldToken
    }
}