package com.example.tutopiaapplication.presentation.auth.login.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.domain.model.RegisterEvent
import com.example.tutopiaapplication.domain.model.RegisterState
import com.example.tutopiaapplication.domain.model.ValidationRegisterEvent
import com.example.tutopiaapplication.domain.model.login.LoginUserEvent
import com.example.tutopiaapplication.domain.model.login.LoginUserState
import com.example.tutopiaapplication.domain.model.login.ValidationLoginUserEvent
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val requestOtpUseCase: RequestOtpUseCase,
    private val validateMobileNo: ValidateMobileNumber,
    private val validatePassword: ValidatePassword
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUserState())
    val loginState: StateFlow<LoginUserState> = _loginState

    private val _loginStateFlow =
        MutableStateFlow<Resource<LoginResponseEntity>>(Resource.Loading(null))
    val loginStateFlow: StateFlow<Resource<LoginResponseEntity>>
        get() = _loginStateFlow

    private val _requestOtpStateFlow =
        MutableStateFlow<Resource<OtpResponseEntity>>(Resource.Loading(null))
    val requestOtpStateFlow : StateFlow<Resource<OtpResponseEntity>>
        get() =  _requestOtpStateFlow

    private val validationRequestOtpEventChannel = Channel<ValidationLoginUserEvent>()
    val validationRequestOtpEvents = validationRequestOtpEventChannel.receiveAsFlow()

    private val validationLoginEventChannel = Channel<ValidationLoginUserEvent>()
    val validationLoginEvents = validationLoginEventChannel.receiveAsFlow()

    fun onEvent(event: LoginUserEvent) {
        when (event) {
            is LoginUserEvent.MobileNumberChanged -> {
                _loginState.value = _loginState.value.copy(mobileNo = event.mobileNumber)
            }
            is LoginUserEvent.PasswordChanged -> {
                _loginState.value = _loginState.value.copy(password = event.password)
            }
            is LoginUserEvent.RequestOtp -> {
                requestOtpData()
            }
            is LoginUserEvent.LoginUser -> {
                loginData()
            }
        }
    }

    private fun loginData() {
        val mobileNumberResult = validateMobileNo.execute(_loginState.value.mobileNo)
        val passwordResult = validatePassword.execute(_loginState.value.password)


        val hasError = listOf(
            mobileNumberResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            _loginState.value = _loginState.value.copy(
                mobileNoError = mobileNumberResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )

            viewModelScope.launch {
                validationLoginEventChannel.send(
                    ValidationLoginUserEvent.Error(
                        _loginState.value.mobileNoError,
                        _loginState.value.passwordError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            validationLoginEventChannel.send(
                ValidationLoginUserEvent.Success(
                    _loginState.value.mobileNo,
                    _loginState.value.password
                )
            )
        }
    }

    private fun  requestOtpData() {
        val mobileNumberResult = validateMobileNo.execute(_loginState.value.mobileNo)

        val hasError = listOf(
            mobileNumberResult
        ).any { !it.successful }

        if (hasError) {
            _loginState.value = _loginState.value.copy(
                mobileNoError = mobileNumberResult.errorMessage
            )
            viewModelScope.launch {
                validationRequestOtpEventChannel.send(
                    ValidationLoginUserEvent.Error(
                        _loginState.value.mobileNoError,
                        _loginState.value.passwordError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            validationRequestOtpEventChannel.send(
                ValidationLoginUserEvent.Success(
                    _loginState.value.mobileNo,
                    _loginState.value.password
                )
            )
        }
    }

    fun loginUser(
        loginRequestEntity: LoginRequestEntity
    ) = viewModelScope.launch {
        loginUserUseCase.execute(loginRequestEntity).collect {
           _loginStateFlow.value = it
        }
    }

    fun requestOtpUser(
        otpRequestEntity: OtpRequestEntity
    ) = viewModelScope.launch {
        requestOtpUseCase.execute(otpRequestEntity).collect {
            _requestOtpStateFlow.value = it
        }
    }

}