package com.example.tutopiaapplication.domain.model

sealed class ValidationRegisterEvent {
    data class Success(val name: String, val mobileNo: String,val board: String,val className: String): ValidationRegisterEvent()
    data class Error(val nameError: String?, val mobileNoError: String?,val boardError: String?,val classNameError: String?)  :ValidationRegisterEvent()
}
