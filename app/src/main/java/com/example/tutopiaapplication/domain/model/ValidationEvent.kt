package com.example.tutopiaapplication.domain.model

sealed class ValidationEvent {
    data class Success(val name: String, val mobileNo: String,val board: String,val className: String): ValidationEvent()
    data class Error(val nameError: String?, val mobileNoError: String?,val boardError: String?,val classNameError: String?)  :ValidationEvent()
}
