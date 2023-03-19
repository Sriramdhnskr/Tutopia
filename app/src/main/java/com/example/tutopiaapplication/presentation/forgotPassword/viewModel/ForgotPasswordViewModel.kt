package com.example.tutopiaapplication.presentation.forgotPassword.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolDetails
import com.example.tutopiaapplication.domain.model.*
import com.example.tutopiaapplication.domain.model.login.LoginUserEvent
import com.example.tutopiaapplication.domain.model.login.ValidationLoginUserEvent
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val _forgotPasswordStateFlow =
        MutableStateFlow<Resource<ForgotPasswordResponseEntity>>(Resource.Loading(null))
    val forgotPasswordStateFlow: StateFlow<Resource<ForgotPasswordResponseEntity>>
        get() = _forgotPasswordStateFlow

    fun forgotPassword(
        otpRequestEntity: OtpRequestEntity
    ) = viewModelScope.launch {
        forgotPasswordUseCase.execute(otpRequestEntity).collect {
            _forgotPasswordStateFlow.value = it
        }
    }


}