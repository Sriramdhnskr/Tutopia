package com.example.tutopiaapplication.domain.model.login

data class ChangePasswordState(
    val password: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null,
    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,
    )
