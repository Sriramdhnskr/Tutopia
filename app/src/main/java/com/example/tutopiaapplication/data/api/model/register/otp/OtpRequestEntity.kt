package com.example.tutopiaapplication.data.api.model.register.otp

data class OtpRequestEntity(
    val country_code: String = "+91",
    val mobile: String?
)