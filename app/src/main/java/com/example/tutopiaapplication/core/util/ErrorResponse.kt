package com.example.tutopiaapplication.core.util

data class ErrorResponse(
    val error: List<String>,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)