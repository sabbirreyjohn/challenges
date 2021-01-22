package com.tamboon.androidclient.view.donation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tamboon.androidclient.model.Payment
import com.tamboon.androidclient.model.ResponseType
import com.tamboon.androidclient.network.NetworkApi
import com.tamboon.androidclient.network.RetrofitInitializer

class DonationViewModel(application: Application) : AndroidViewModel(application) {

    var networkApi: NetworkApi = RetrofitInitializer.getInstance().create(NetworkApi::class.java)

    fun sendDonationToOmise(payment: Payment): MutableLiveData<ResponseType> {
        return DonationRepository.sendDonationToOmise(payment)
    }

    fun postDonationToServer(donation: Donation): MutableLiveData<ResponseType> {
        return DonationRepository.postDonationToServer(networkApi, donation)
    }
}