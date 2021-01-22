package com.tamboon.androidclient.model

import com.tamboon.androidclient.R
import java.io.Serializable

data class ResponseType(
    var succeed: Boolean,
    var message: String?,
    var icon: Int = R.drawable.ic_success
) : Serializable