package com.example.tutopiaapplication.data.api.model.login

data class LoginResponseEntity(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)