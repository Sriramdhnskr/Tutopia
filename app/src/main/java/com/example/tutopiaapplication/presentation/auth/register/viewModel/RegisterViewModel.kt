package com.example.tutopiaapplication.presentation.auth.register.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.domain.model.*
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val getClassesListUseCase: GetClassesListUseCase,
    private val getBoardsListUseCase: GetBoardsListUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateUserName: ValidateName,
    private val validateMobileNo: ValidateMobileNumber,
    private val validateBoard: ValidateBoard,
    private val validateClass: ValidateClass
) : ViewModel() {

    private val _getBoardsList = MutableStateFlow<Resource<List<Board>>>(Resource.Loading(null))
    val getBoardsList: StateFlow<Resource<List<Board>>>
        get() = _getBoardsList

    private val _getClassesList =
        MutableStateFlow<Resource<List<ClassDetails>>>(Resource.Loading(null))
    val getClasses: StateFlow<Resource<List<ClassDetails>>>
        get() = _getClassesList

    private val _registrationStateFlow =
        MutableStateFlow<Resource<RegisterResponseEntity>>(Resource.Loading(null))
    val registrationStateFlow: StateFlow<Resource<RegisterResponseEntity>>
        get() = _registrationStateFlow

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String> = _errorEvent

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getBoardsListUseCase.execute().collect {
                val boards = it
                Log.i("boards", "${boards}")
                _getBoardsList.value = boards
            }
        }
        viewModelScope.launch {
            getClassesListUseCase.execute().collect {
                val classes = it
                Log.i("classes", "${classes}")
                _getClassesList.value = classes
            }
        }
    }

    fun setName(name: String) {
        val student = _registerState.value.copy(name = name)
        _registerState.value = student
    }

    fun setMobileNumber(mobileNumber: String) {
        val student = _registerState.value.copy(mobileNumber = mobileNumber)
        _registerState.value = student
    }

    fun setBoard(board: String) {
        val student = _registerState.value.copy(board = board)
        _registerState.value = student
    }

    fun setStudentClass(studentClass: String) {
        val student = _registerState.value.copy(className = studentClass)
        _registerState.value = student
    }

    /*fun setClasses() = viewModelScope.launch {
                getClassesListUseCase.execute().collect {
                    val classes = it
                    Log.i("classes", "${classes}")
                    _getClassesList.value = classes
                }
    }*/

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged -> {
                _registerState.value = _registerState.value.copy(name = event.name)
            }
            is RegisterEvent.MobileNumberChanged -> {
                _registerState.value = _registerState.value.copy(mobileNumber = event.mobileNo)
            }
            is RegisterEvent.SelectedBoardChanged -> {
                _registerState.value = _registerState.value.copy(board = event.board)
            }
            is RegisterEvent.SelectedClassChanged -> {
                _registerState.value = _registerState.value.copy(className = event.className)
            }
            is RegisterEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val usernameResult = validateUserName.execute(_registerState.value.name)
        val mobileNumberResult = validateMobileNo.execute(_registerState.value.mobileNumber)
        val boardResult = validateBoard.execute(_registerState.value.board)
        val classNameResult = validateClass.execute(_registerState.value.className)

        val hasError = listOf(
            usernameResult,
            mobileNumberResult,
            boardResult,
            classNameResult
        ).any { !it.successful }

        if (hasError) {
            _registerState.value = _registerState.value.copy(
                nameError = usernameResult.errorMessage,
                mobileNoError = mobileNumberResult.errorMessage,
                boardError = boardResult.errorMessage,
                classNameError = classNameResult.errorMessage
            )
            Log.i("LoginViewModel", "usernameres : ${usernameResult}")

            viewModelScope.launch {
                validationEventChannel.send(
                    ValidationEvent.Error(
                        _registerState.value.nameError,
                        _registerState.value.mobileNoError,
                        registerState.value.boardError,
                        _registerState.value.classNameError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(
                ValidationEvent.Success(
                    _registerState.value.name,
                    _registerState.value.mobileNumber,
                    registerState.value.board,
                    _registerState.value.className
                )
            )
        }
    }


    fun register(
        registerRequestEntity: RegisterRequestEntity
    ) = viewModelScope.launch {
        registerUserUseCase.execute(registerRequestEntity).collect {
            _registrationStateFlow.value = it
        }
    }

//    fun getBoardLists(
//    ) = viewModelScope.launch {
//        getBoardsListUseCase.execute().collect{
//            val boards = it
//            Log.i("boards","${boards}")
//            _getBoardsList.value = boards
//        }
//    }

/*
    fun onSubmit() {
        val student = _studentState.value

        if (student.name.isEmpty()) {
            viewModelScope.launch {
                _errorEvent.emit("Please provide your name")
            }
            return
        }

        if (!isMobileNumberValid(student.mobileNumber)) {
            viewModelScope.launch {
                _errorEvent.emit("Please provide a valid 9-digit mobile number")
            }
            return




        }
        // Submit the student details to the backend API
    }*/

    /* private fun isMobileNumberValid(mobileNumber: String): Boolean {
         return mobileNumber.length == 9 && mobileNumber.isDigitsOnly()
     }*/

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent
}