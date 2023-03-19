package com.example.tutopiaapplication.presentation.auth.otp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OtpViewModel(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val verifyLoginUseCase: VerifyLoginUseCase,
    private val requestOtpUseCase: RequestOtpUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel() {

    private val _verifyOtpStateFlow =
        MutableStateFlow<Resource<VerifyOtpResponseEntity>>(Resource.Loading(null))
    val verifyOtpStateFlow: StateFlow<Resource<VerifyOtpResponseEntity>>
        get() = _verifyOtpStateFlow

    private val _verifyLoginStateFlow =
        MutableStateFlow<Resource<VerifyLoginResponseEntity>>(Resource.Loading(null))
    val verifyLoginStateFlow: StateFlow<Resource<VerifyLoginResponseEntity>>
        get() = _verifyLoginStateFlow

    private val _resendOtpStateFlow =
        MutableStateFlow<Resource<RegisterResponseEntity>>(Resource.Loading(null))
    val resendOtpStateFlow : StateFlow<Resource<RegisterResponseEntity>>
        get() = _resendOtpStateFlow

    private val _forgotPasswordStateFlow =
        MutableStateFlow<Resource<ForgotPasswordResponseEntity>>(Resource.Loading(null))
    val forgotPasswordStateFlow : StateFlow<Resource<ForgotPasswordResponseEntity>>
        get() = _forgotPasswordStateFlow

    private val _requestOtpStateFlow =
        MutableStateFlow<Resource<OtpResponseEntity>>(Resource.Loading(null))
    val requestOtpStateFlow : StateFlow<Resource<OtpResponseEntity>>
        get() = _requestOtpStateFlow

    fun verifyOtp(
        requestEntity: VerifyOtpRequestEntity
    ) = viewModelScope.launch {
           verifyOtpUseCase.execute(requestEntity).collect {
            _verifyOtpStateFlow.value = it
        }
    }

    fun verifyLogin(
        requestEntity: VerifyLoginRequestEntity
    ) = viewModelScope.launch {
        verifyLoginUseCase.execute(requestEntity).collect {
            _verifyLoginStateFlow.value = it
        }
    }

    fun register(
        registerRequestEntity: RegisterRequestEntity
    ) = viewModelScope.launch {
       registerUserUseCase.execute(registerRequestEntity).collect {
            _resendOtpStateFlow.value = it
        }
    }

    fun resendOtp(
       otpRequestEntity: OtpRequestEntity
    ) = viewModelScope.launch {
        requestOtpUseCase.execute(otpRequestEntity).collect {
            _requestOtpStateFlow.value = it
        }
    }

    fun forgotPassword(
        otpRequestEntity: OtpRequestEntity
    ) = viewModelScope.launch {
        forgotPasswordUseCase.execute(otpRequestEntity).collect {
            _forgotPasswordStateFlow.value = it
        }
    }

}