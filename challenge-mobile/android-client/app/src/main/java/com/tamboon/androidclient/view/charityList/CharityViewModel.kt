package com.tamboon.androidclient.view.charityList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tamboon.androidclient.network.NetworkApi
import com.tamboon.androidclient.network.RetrofitInitializer

class CharityViewModel(application: Application) : AndroidViewModel(application) {


    var networkApi: NetworkApi = RetrofitInitializer.getInstance().create(NetworkApi::class.java)

    fun getCharities(): MutableLiveData<List<Charity>> {
        return CharityRepository.getCharities(networkApi)
    }

}