package com.example.tutopiaapplication.domain.model

sealed class ValidationSchoolNameEvent {
    data class Success( val pinCode: String): ValidationSchoolNameEvent()
    data class Error(val pinCodeError: String?)  :ValidationSchoolNameEvent()
}