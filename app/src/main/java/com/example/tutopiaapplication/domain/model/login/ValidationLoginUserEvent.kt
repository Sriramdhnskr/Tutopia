package com.example.tutopiaapplication.domain.model.login

sealed class ValidationLoginUserEvent {
    data class Success( val mobileNo: String, val password: String): ValidationLoginUserEvent()
    data class Error( val mobileNoError: String?, val passwordError: String?)  :ValidationLoginUserEvent()
}