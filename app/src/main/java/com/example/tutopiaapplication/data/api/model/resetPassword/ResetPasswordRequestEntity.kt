package com.example.tutopiaapplication.data.api.model.resetPassword

data class ResetPasswordRequestEntity(
    val confirm_password: String,
    val country_code: String = "+91",
    val mobile: String,
    val otp: String,
    val password: String
)