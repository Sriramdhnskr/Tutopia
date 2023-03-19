package com.example.tutopiaapplication.data.api.model.login

data class Data(
    val access_token: String?,
    val otp: String?,
    val profile: Profile?,
    val userId: String?,
    val verify_otp: Boolean?
)