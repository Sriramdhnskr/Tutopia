package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.domain.usecase.*
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
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
    fun provideMovieViewModelFactory(
        getClassesListUseCase: GetClassesListUseCase,
        getBoardsListUseCase: GetBoardsListUseCase,
        registerUserUseCase: RegisterUserUseCase,
        validateName: ValidateName,
        validateMobileNumber: ValidateMobileNumber,
        validateBoard: ValidateBoard,
        validateClass: ValidateClass
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(getClassesListUseCase,getBoardsListUseCase,registerUserUseCase,validateName,validateMobileNumber,validateBoard,validateClass)
    }

}