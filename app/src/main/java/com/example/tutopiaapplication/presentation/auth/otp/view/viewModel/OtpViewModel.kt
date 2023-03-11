package com.example.tutopiaapplication.presentation.auth.otp.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.domain.usecase.RegisterUserUseCase
import com.example.tutopiaapplication.domain.usecase.RequestOtpUseCase
import com.example.tutopiaapplication.domain.usecase.VerifyOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OtpViewModel(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {

    private val _verifyOtpStateFlow =
        MutableStateFlow<Resource<VerifyOtpResponseEntity>>(Resource.Loading(null))
    val verifyOtpStateFlow: StateFlow<Resource<VerifyOtpResponseEntity>>
        get() = _verifyOtpStateFlow

    private val _resendOtpStateFlow =
        MutableStateFlow<Resource<RegisterResponseEntity>>(Resource.Loading(null))
    val resendOtpStateFlow : StateFlow<Resource<RegisterResponseEntity>>
        get() = _resendOtpStateFlow

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

    fun register(
        registerRequestEntity: RegisterRequestEntity
    ) = viewModelScope.launch {
       registerUserUseCase.execute(registerRequestEntity).collect {
            _resendOtpStateFlow.value = it
        }
    }

   /* fun resendOtp(
       otpRequestEntity: OtpRequestEntity
    ) = viewModelScope.launch {
        requestOtpUseCase.execute(otpRequestEntity).collect {
            _requestOtpStateFlow.value = it
        }
    }*/

}