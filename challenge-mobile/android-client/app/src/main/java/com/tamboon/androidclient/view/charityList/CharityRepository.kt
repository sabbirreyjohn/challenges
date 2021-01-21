package com.tamboon.androidclient.view.charityList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tamboon.androidclient.network.CharityApi
import com.tamboon.androidclient.network.RetrofitInitializer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object CharityRepository {


    fun getCharities(charityApi: CharityApi): MutableLiveData<List<Charity>> {
        var ldCharities = MutableLiveData<List<Charity>>()
        val disposable = charityApi.getCharities().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    Log.i("Charities", it.toString())
                    ldCharities.value = it.data
                },
                {
                    Log.i("Charities", it.message)
                    ldCharities.value = null

                })
        return ldCharities
    }
}