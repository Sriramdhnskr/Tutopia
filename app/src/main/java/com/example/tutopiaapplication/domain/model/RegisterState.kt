package com.example.tutopiaapplication.domain.model

data class RegisterState(
    val name: String = "",
    val mobileNumber: String ="",
    val board: String = "",
    val className: String = "",
    val nameError: String? = null,
    val mobileNoError: String? = null,
    val boardError: String? = null,
    val classNameError: String? = null
)
