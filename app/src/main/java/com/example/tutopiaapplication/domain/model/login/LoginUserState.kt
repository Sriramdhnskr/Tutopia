package com.example.tutopiaapplication.domain.model.login

data class LoginUserState(
    val mobileNo: String = "",
    val password: String = "",
    val mobileNoError: String? = null,
    val passwordError: String? = null,
)
