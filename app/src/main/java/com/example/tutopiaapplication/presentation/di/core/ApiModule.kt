package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.domain.usecase.*
import com.example.tutopiaapplication.presentation.auth.otp.view.viewModel.OtpViewModelFactory
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
import com.example.tutopiaapplication.presentation.completeProfile.viewModel.CompleteProfileViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRegisterViewModelFactory(
        getClassesListUseCase: GetClassesListUseCase,
        getBoardsListUseCase: GetBoardsListUseCase,
        registerUserUseCase: RegisterUserUseCase,
        validateName: ValidateName,
        validateMobileNumber: ValidateMobileNumber,
        validateBoard: ValidateBoard,
        validateClass: ValidateClass
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            getClassesListUseCase,
            getBoardsListUseCase,
            registerUserUseCase,
            validateName,
            validateMobileNumber,
            validateBoard,
            validateClass
        )
    }

    @Singleton
    @Provides
    fun provideOtpViewModelFactory(
        verifyOtpUseCase: VerifyOtpUseCase,
        registerUserUseCase: RegisterUserUseCase
    ): OtpViewModelFactory {
        return OtpViewModelFactory(verifyOtpUseCase, registerUserUseCase)
    }

    @Singleton
    @Provides
    fun provideCompleteProfileViewModelFactory(
        updateProfileUseCase: UpdateProfileUseCase,
        schoolsListUseCase: GetSchoolsListUseCase,
        validatePinCode: ValidatePinCode,
        validateSchool: ValidateSchool,
        validatePassword: ValidatePassword,
        validateConfirmPassword: ValidateConfirmPassword
        ): CompleteProfileViewModelFactory {
        return CompleteProfileViewModelFactory(updateProfileUseCase,schoolsListUseCase,validatePinCode, validateSchool, validatePassword, validateConfirmPassword)
    }
}