package com.example.tutopiaapplication.domain.model

sealed class RegisterEvent {
    data class NameChanged(val name: String) :RegisterEvent()
    data class MobileNumberChanged(val mobileNo: String) :RegisterEvent()
    data class SelectedBoardChanged(val board: String) :RegisterEvent()
    data class SelectedClassChanged(val className: String) :RegisterEvent()
    object Submit:RegisterEvent()
}
