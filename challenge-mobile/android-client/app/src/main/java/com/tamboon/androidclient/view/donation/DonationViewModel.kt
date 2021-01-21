package com.tamboon.androidclient.view.donation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tamboon.androidclient.model.Payment

class DonationViewModel(application: Application) : AndroidViewModel(application) {

    fun sendDonationToOmise(payment: Payment): MutableLiveData<String> {
        return DonationRepository.sendDonationToOmise(payment)
    }
}