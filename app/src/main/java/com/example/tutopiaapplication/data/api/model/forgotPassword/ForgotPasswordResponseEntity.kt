package com.example.tutopiaapplication.data.api.model.forgotPassword

data class ForgotPasswordResponseEntity(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)