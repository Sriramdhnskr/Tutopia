package com.example.tutopiaapplication.data.api.model.login.verifyLogin

data class Data(
    val access_token: String,
    val profile: Profile,
    val verify_otp: Boolean
)