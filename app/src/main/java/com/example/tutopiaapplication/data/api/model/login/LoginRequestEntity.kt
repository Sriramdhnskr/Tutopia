package com.example.tutopiaapplication.data.api.model.login

data class LoginRequestEntity(
    val country_code: String = "+91",
    val device_token: String,
    val fcm_token: String = "",
    val mobile: String,
    val password: String,
    val platform: String,
    val version: String
)