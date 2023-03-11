package com.example.tutopiaapplication.data.api.model.register.verifyOtp

data class VerifyOtpRequestEntity(
    val device_token: String?,
    val fcm_token: String?,
    val otp: String?,
    val platform: String?,
    val user_id: Int?
)