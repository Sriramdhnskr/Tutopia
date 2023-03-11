package com.example.tutopiaapplication.presentation.auth.otp.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*

class OtpViewModelFactory(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OtpViewModel(
           verifyOtpUseCase,
           registerUserUseCase
        ) as T
    }
}