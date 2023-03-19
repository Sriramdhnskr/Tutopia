package com.example.tutopiaapplication.domain.model.password

sealed class ChangePasswordEvent {
    data class PasswordChanged(val password : String) : ChangePasswordEvent()
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ChangePasswordEvent()
    object SavePassword : ChangePasswordEvent()
}