package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.domain.repository.RegisterRepository
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

}