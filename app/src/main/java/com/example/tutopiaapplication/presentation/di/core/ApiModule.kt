package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.domain.usecase.*
import com.example.tutopiaapplication.presentation.auth.otp.view.viewModel.LoginViewModelFactory
import com.example.tutopiaapplication.presentation.auth.otp.viewModel.OtpViewModelFactory
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
import com.example.tutopiaapplication.presentation.completeProfile.viewModel.CompleteProfileViewModelFactory
import com.example.tutopiaapplication.presentation.forgotPassword.viewModel.ForgotPasswordViewModelFactory
import com.example.tutopiaapplication.presentation.resetPassword.viewModel.ResetPasswordViewModelFactory
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
        registerUserUseCase: RegisterUserUseCase,
        verifyLoginUseCase: VerifyLoginUseCase,
        requestOtpUseCase: RequestOtpUseCase,
        forgotPasswordUseCase: ForgotPasswordUseCase
    ): OtpViewModelFactory {
        return OtpViewModelFactory(
            verifyOtpUseCase,
            registerUserUseCase,
            verifyLoginUseCase,
            requestOtpUseCase,
            forgotPasswordUseCase
        )
    }

    @Singleton
    @Provides
    fun provideForgotPasswordViewModelFactory(
        forgotPasswordUseCase: ForgotPasswordUseCase
    ): ForgotPasswordViewModelFactory {
        return ForgotPasswordViewModelFactory(forgotPasswordUseCase)
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
        return CompleteProfileViewModelFactory(
            updateProfileUseCase,
            schoolsListUseCase,
            validatePinCode,
            validateSchool,
            validatePassword,
            validateConfirmPassword
        )
    }

    @Singleton
    @Provides
    fun provideLoginViewModelFactory(
        loginUserUseCase: LoginUserUseCase,
        requestOtpUseCase: RequestOtpUseCase,
        validateMobileNumber: ValidateMobileNumber,
        validatePassword: ValidatePassword
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            loginUserUseCase,
            requestOtpUseCase,
            validateMobileNumber,
            validatePassword
        )
    }


    @Singleton
    @Provides
    fun provideResetPasswordViewModelFactory(
        resetPasswordUseCase: ResetPasswordUseCase,
        validatePassword: ValidatePassword,
        validateConfirmPassword: ValidateConfirmPassword
    ): ResetPasswordViewModelFactory {
        return ResetPasswordViewModelFactory(
           resetPasswordUseCase,
            validatePassword,
            validateConfirmPassword
        )
    }

}