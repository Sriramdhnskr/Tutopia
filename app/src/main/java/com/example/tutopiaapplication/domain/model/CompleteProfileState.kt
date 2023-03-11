package com.example.tutopiaapplication.domain.model

import java.io.File

data class CompleteProfileState(
    val pinCode: String = "",
    val schoolName: String ="",
    val schoolCode: String ="",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImage : File? = null,
    val pinCodeError: String? = null,
    val schoolError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)
