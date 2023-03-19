package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.domain.repository.*
import com.example.tutopiaapplication.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

   /* @Singleton
    @Provides
    fun provideRequestTokenUseCase(loginRepository: LoginRepository) : GetRequestTokenUseCase{
        return GetRequestTokenUseCase(loginRepository)
    }*/

    @Singleton
    @Provides
    fun provideRegisterNameUseCase() : ValidateName{
        return ValidateName()
    }

    @Singleton
    @Provides
    fun provideRegisterMobileNumberUseCase() : ValidateMobileNumber {
        return ValidateMobileNumber()
    }

    @Singleton
    @Provides
    fun provideRegisterSelectedBoardUseCase() : ValidateBoard {
        return ValidateBoard()
    }

    @Singleton
    @Provides
    fun provideRegisterSelectedClassUseCase() : ValidateClass {
        return ValidateClass()
    }

    @Singleton
    @Provides
    fun provideUpdatePinCodeUseCase() : ValidatePinCode{
        return ValidatePinCode()
    }

    @Singleton
    @Provides
    fun provideUpdateSchoolUseCase() : ValidateSchool{
        return ValidateSchool()
    }

    @Singleton
    @Provides
    fun provideUpdatePasswordUseCase() : ValidatePassword{
        return ValidatePassword()
    }

    @Singleton
    @Provides
    fun provideUpdateConfirmPasswordUseCase() : ValidateConfirmPassword{
        return ValidateConfirmPassword()
    }

    @Singleton
    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository) : RegisterUserUseCase {
        return RegisterUserUseCase(registerRepository)
    }

    @Singleton
    @Provides
    fun provideGetBoardsListUseCase(registerRepository: RegisterRepository) : GetBoardsListUseCase {
        return GetBoardsListUseCase(registerRepository)
    }

    @Singleton
    @Provides
    fun provideGetClassListUseCase(registerRepository: RegisterRepository) : GetClassesListUseCase {
        return GetClassesListUseCase(registerRepository)
    }

    @Singleton
    @Provides
    fun provideVerifyOtpUseCase(otpRepository: OtpRepository) : VerifyOtpUseCase {
        return VerifyOtpUseCase(otpRepository)
    }

    @Singleton
    @Provides
    fun provideRequestOtpUseCase(otpRepository: OtpRepository) : RequestOtpUseCase {
        return RequestOtpUseCase(otpRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileUseCase(updateProfileRepository: UpdateProfileRepository) : UpdateProfileUseCase {
        return UpdateProfileUseCase(updateProfileRepository)
    }

    @Singleton
    @Provides
    fun provideGetSchoolListUseCase(updateProfileRepository: UpdateProfileRepository) : GetSchoolsListUseCase {
        return GetSchoolsListUseCase(updateProfileRepository)
    }

    @Singleton
    @Provides
    fun provideLoginUserUseCase(loginRepository: LoginRepository) : LoginUserUseCase {
        return LoginUserUseCase(loginRepository)
    }

    @Singleton
    @Provides
    fun provideVerifyLoginUseCase(otpRepository: OtpRepository) : VerifyLoginUseCase {
        return VerifyLoginUseCase(otpRepository)
    }

    @Singleton
    @Provides
    fun provideForgotPasswordUseCase(passwordRepository: PasswordRepository) : ForgotPasswordUseCase {
        return ForgotPasswordUseCase(passwordRepository)
    }

    @Singleton
    @Provides
    fun provideResetPasswordUseCase(passwordRepository: PasswordRepository) : ResetPasswordUseCase {
        return ResetPasswordUseCase(passwordRepository)
    }
}