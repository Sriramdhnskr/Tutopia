package com.example.tutopiaapplication.domain.model.password

sealed class ValidationChangePasswordEvent {
    data class Success(
        val password: String?=null,
        val newPassword: String,
        val confirmPassword: String
    ) : ValidationChangePasswordEvent()

    data class Error(
        val passwordError: String? = null,
        val newPasswordError: String? = null,
        val confirmPasswordError: String? = null
    ) :
        ValidationChangePasswordEvent()
}