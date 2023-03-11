package com.example.tutopiaapplication.data.api.model.register.otp

data class OtpResponseEntity(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)