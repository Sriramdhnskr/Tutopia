package com.example.tutopiaapplication.presentation.resetPassword.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordResponseEntity
import com.example.tutopiaapplication.domain.model.login.ChangePasswordState
import com.example.tutopiaapplication.domain.model.login.LoginUserEvent
import com.example.tutopiaapplication.domain.model.login.LoginUserState
import com.example.tutopiaapplication.domain.model.login.ValidationLoginUserEvent
import com.example.tutopiaapplication.domain.model.password.ChangePasswordEvent
import com.example.tutopiaapplication.domain.model.password.ValidationChangePasswordEvent
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword
) : ViewModel() {

    private val _resetPasswordState = MutableStateFlow(ChangePasswordState())
    val resetPasswordState: StateFlow<ChangePasswordState> = _resetPasswordState

    private val _resetPasswordStateFlow =
        MutableStateFlow<Resource<ResetPasswordResponseEntity>>(Resource.Loading(null))
    val resetPasswordStateFlow: StateFlow<Resource<ResetPasswordResponseEntity>>
        get() = _resetPasswordStateFlow

    private val validationChangePasswordEvent = Channel<ValidationChangePasswordEvent>()
    val validationChangePasswordEvents = validationChangePasswordEvent.receiveAsFlow()

    fun resetPassword(
        resetPasswordRequestEntity: ResetPasswordRequestEntity
    ) = viewModelScope.launch {
        resetPasswordUseCase.execute(resetPasswordRequestEntity).collect {
            _resetPasswordStateFlow.value = it
        }
    }

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.PasswordChanged -> {
                _resetPasswordState.value =
                    _resetPasswordState.value.copy(password = event.password)
            }
            is ChangePasswordEvent.NewPasswordChanged -> {
                _resetPasswordState.value =
                    _resetPasswordState.value.copy(newPassword = event.newPassword)
            }
            is ChangePasswordEvent.ConfirmPasswordChanged -> {
                _resetPasswordState.value =
                    _resetPasswordState.value.copy(confirmPassword = event.confirmPassword)
            }
            is ChangePasswordEvent.SavePassword -> {
                saveData()
            }
        }
    }

    private fun saveData() {
        val passwordResult = validatePassword.execute(_resetPasswordState.value.newPassword)
        val confirmPasswordResult =
            validateConfirmPassword.execute(_resetPasswordState.value.confirmPassword,_resetPasswordState.value.newPassword)


        val hasError = listOf(
            passwordResult,
            confirmPasswordResult
        ).any { !it.successful }

        if (hasError) {
            _resetPasswordState.value = _resetPasswordState.value.copy(
                newPasswordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )

            viewModelScope.launch {
                validationChangePasswordEvent.send(
                    ValidationChangePasswordEvent.Error(
                        newPasswordError = _resetPasswordState.value.newPasswordError,
                        confirmPasswordError = _resetPasswordState.value.confirmPasswordError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            validationChangePasswordEvent.send(
                ValidationChangePasswordEvent.Success(
                    newPassword = _resetPasswordState.value.newPassword,
                    confirmPassword = _resetPasswordState.value.confirmPassword
            )
            )
        }
    }
}