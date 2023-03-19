package com.example.tutopiaapplication.presentation.forgotPassword.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*
import com.example.tutopiaapplication.presentation.completeProfile.viewModel.CompleteProfileViewModel

class ForgotPasswordViewModelFactory(
  private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(
        forgotPasswordUseCase
        ) as T
    }
}