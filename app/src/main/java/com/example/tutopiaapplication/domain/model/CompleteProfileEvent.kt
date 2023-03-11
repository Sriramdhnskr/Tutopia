package com.example.tutopiaapplication.domain.model

import java.io.File

sealed class CompleteProfileEvent {
    data class PinCodeChanged(val pinCode: String) : CompleteProfileEvent()
    data class SelectedSchoolChanged(val schoolCode: String,val schoolName : String) : CompleteProfileEvent()
    data class PasswordChanged(val password: String) : CompleteProfileEvent()
    data class ImageFileChanged(val image : File? = null) : CompleteProfileEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : CompleteProfileEvent()
    object Submit : CompleteProfileEvent()
    object SchoolChanged : CompleteProfileEvent()
}