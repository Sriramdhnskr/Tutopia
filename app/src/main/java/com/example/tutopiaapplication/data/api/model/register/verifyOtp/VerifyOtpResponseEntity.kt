package com.example.tutopiaapplication.data.api.model.register.verifyOtp

data class VerifyOtpResponseEntity(
    val data:Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)