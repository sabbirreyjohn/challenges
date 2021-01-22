package com.tamboon.androidclient.view.charityList

import com.tamboon.androidclient.network.NetworkApi
import com.tamboon.androidclient.network.RetrofitInitializer
import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*

class CharityRepositoryTest : TestCase() {


    var networkApi = RetrofitInitializer.getInstance().create(NetworkApi::class.java)

    @Test
    fun getCharities() {
        
    }
}