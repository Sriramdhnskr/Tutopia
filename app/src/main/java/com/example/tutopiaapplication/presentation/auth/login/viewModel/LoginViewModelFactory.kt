package com.example.tutopiaapplication.presentation.auth.otp.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*
import com.example.tutopiaapplication.presentation.auth.login.viewModel.LoginViewModel

class LoginViewModelFactory(
    private val loginUserUseCase: LoginUserUseCase,
    private val requestOtpUseCase: RequestOtpUseCase,
    private val validateMobileNo: ValidateMobileNumber,
    private val validatePassword: ValidatePassword
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            loginUserUseCase,
            requestOtpUseCase,
            validateMobileNo,
            validatePassword
        ) as T
    }
}