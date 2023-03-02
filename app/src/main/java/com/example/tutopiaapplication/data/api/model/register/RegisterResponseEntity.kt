package com.example.tutopiaapplication.data.api.model.register

data class RegisterResponseEntity(
    val data: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)