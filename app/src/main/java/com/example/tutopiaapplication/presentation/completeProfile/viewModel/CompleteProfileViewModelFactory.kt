package com.example.tutopiaapplication.presentation.completeProfile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*

class CompleteProfileViewModelFactory(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getSchoolsListUseCase: GetSchoolsListUseCase,
    private val validatePinCode: ValidatePinCode,
    private val validateSchool: ValidateSchool,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CompleteProfileViewModel(
          updateProfileUseCase,
          getSchoolsListUseCase,
            validatePinCode,
            validateSchool,
            validatePassword,
            validateConfirmPassword
        ) as T
    }
}