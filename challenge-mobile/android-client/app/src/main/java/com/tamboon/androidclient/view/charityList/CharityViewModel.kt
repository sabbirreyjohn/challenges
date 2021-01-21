package com.tamboon.androidclient.view.charityList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tamboon.androidclient.network.CharityApi
import com.tamboon.androidclient.network.RetrofitInitializer

class CharityViewModel(application: Application) : AndroidViewModel(application) {


    var charityApi: CharityApi = RetrofitInitializer.getInstance().create(CharityApi::class.java)

    fun getCharities(): MutableLiveData<List<Charity>> {
        return CharityRepository.getCharities(charityApi)
    }

}