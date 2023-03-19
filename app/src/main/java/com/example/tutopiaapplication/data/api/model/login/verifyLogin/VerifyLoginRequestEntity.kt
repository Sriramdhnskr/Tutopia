package com.example.tutopiaapplication.data.api.model.login.verifyLogin

data class VerifyLoginRequestEntity(
    val country_code: String = "+91",
    val device_token: String,
    val fcm_token: String = "",
    val mobile: String,
    val otp: String,
    val password: String,
    val platform: String
)