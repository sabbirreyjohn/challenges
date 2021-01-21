package com.tamboon.androidclient.model

import java.io.Serializable

data class Payment(
    val amount: Int,
    val name: String,
    val cardNumber: String,
    val expMonth: Int,
    val expYear: Int,
    val securityCode: String
) : Serializable