package com.example.tutopiaapplication.data.api.model.login.verifyLogin

data class VerifyLoginResponseEntity(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)