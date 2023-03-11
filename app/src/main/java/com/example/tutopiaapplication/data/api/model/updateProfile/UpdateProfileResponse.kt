package com.example.tutopiaapplication.data.api.model.updateProfile

data class UpdateProfileResponse(
    val data: Data,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)