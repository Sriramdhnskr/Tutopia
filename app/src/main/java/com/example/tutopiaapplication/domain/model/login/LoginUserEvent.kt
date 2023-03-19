package com.example.tutopiaapplication.domain.model.login

import java.io.File

sealed class LoginUserEvent {
    data class MobileNumberChanged(val mobileNumber : String) : LoginUserEvent()
    data class PasswordChanged(val password: String) : LoginUserEvent()
    object RequestOtp : LoginUserEvent()
    object LoginUser : LoginUserEvent()
}