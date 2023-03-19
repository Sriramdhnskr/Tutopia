package com.example.tutopiaapplication.presentation.auth.otp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*

class OtpViewModelFactory(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val verifyLoginUseCase: VerifyLoginUseCase,
    private val requestOtpUseCase: RequestOtpUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OtpViewModel(
            verifyOtpUseCase,
            registerUserUseCase,
            verifyLoginUseCase,
            requestOtpUseCase,
            forgotPasswordUseCase
        ) as T
    }
}