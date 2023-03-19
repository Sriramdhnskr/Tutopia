package com.example.tutopiaapplication.presentation.resetPassword.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*

class ResetPasswordViewModelFactory(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(
            resetPasswordUseCase,
            validatePassword,
            validateConfirmPassword
        ) as T
    }
}