package com.example.tutopiaapplication.domain.model

import java.io.File

sealed class ValidationCompleteProfileEvent {
    data class Success( val pinCode: String, val schoolCode: String, val schoolName: String, val password: String, val confirmPassword: String,val profileImage: File?=null): ValidationCompleteProfileEvent()
    data class Error( val pinCodeError: String?, val schoolError: String?, val passwordError: String?, val confirmPasswordError: String?)  :ValidationCompleteProfileEvent()
}