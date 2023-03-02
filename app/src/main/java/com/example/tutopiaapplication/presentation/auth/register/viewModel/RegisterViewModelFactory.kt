package com.example.tutopiaapplication.presentation.auth.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutopiaapplication.domain.usecase.*

class RegisterViewModelFactory(
    private val getClassesListUseCase: GetClassesListUseCase,
    private val getBoardsListUseCase: GetBoardsListUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateUserName: ValidateName,
    private val validatePassword: ValidateMobileNumber,
    private val validateBoard: ValidateBoard,
    private val validateClass: ValidateClass
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            getClassesListUseCase,
            getBoardsListUseCase,
            registerUserUseCase,
            validateUserName,
            validatePassword,
            validateBoard,
            validateClass
        ) as T
    }
}