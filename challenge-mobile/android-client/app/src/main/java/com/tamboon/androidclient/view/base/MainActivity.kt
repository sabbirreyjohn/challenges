package com.tamboon.androidclient.view.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.tamboon.androidclient.R
import com.tamboon.androidclient.view.charityList.CharityViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    private val charityViewModel: CharityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        charityViewModel.getCharities().observe(this, { charities ->
            if (!charities.isNullOrEmpty()) {
                Log.i(TAG, "onCreate: "+charities.toString())
            }else{
                Log.i(TAG, "onCreate: failed to load")
            }
        })
    }
}