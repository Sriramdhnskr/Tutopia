package com.example.tutopiaapplication.presentation.completeProfile.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolDetails
import com.example.tutopiaapplication.domain.model.*
import com.example.tutopiaapplication.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CompleteProfileViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getSchoolsListUseCase: GetSchoolsListUseCase,
    private val validatePinCode: ValidatePinCode,
    private val validateSchool: ValidateSchool,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword
) : ViewModel() {

    private val _getSchoolsList =
        MutableStateFlow<Resource<List<SchoolDetails>>>(Resource.Loading(null))
    val getSchoolsList: StateFlow<Resource<List<SchoolDetails>>>
        get() = _getSchoolsList

    private val _completeProfileStateFlow =
        MutableStateFlow<Resource<UpdateProfileResponse>>(Resource.Loading(null))
    val completeProfileStateFlow: StateFlow<Resource<UpdateProfileResponse>>
        get() = _completeProfileStateFlow

    private val _completeProfileState = MutableStateFlow(CompleteProfileState())
    val completeProfileState: StateFlow<CompleteProfileState> = _completeProfileState

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String> = _errorEvent

    private val validationEventChannel = Channel<ValidationCompleteProfileEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val schoolValidationEventChannel = Channel<ValidationSchoolNameEvent>()
    val schoolValidationEvents = schoolValidationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getSchoolsListUseCase.execute().collect {
                val schools = it
                Log.i("schools", "${schools}")
                _getSchoolsList.value = schools
            }
        }
    }

    fun onEvent(event: CompleteProfileEvent) {
        when (event) {
            is CompleteProfileEvent.PinCodeChanged -> {
                _completeProfileState.value =
                    _completeProfileState.value.copy(pinCode = event.pinCode)

            }
            is CompleteProfileEvent.SelectedSchoolChanged -> {
                _completeProfileState.value =
                    _completeProfileState.value.copy(schoolName = event.schoolName, schoolCode = event.schoolCode)
            }
            is CompleteProfileEvent.PasswordChanged -> {
                _completeProfileState.value =
                    _completeProfileState.value.copy(password = event.password)

            }
            is CompleteProfileEvent.ConfirmPasswordChanged -> {
                _completeProfileState.value =
                    _completeProfileState.value.copy(confirmPassword = event.confirmPassword)
            }
            is CompleteProfileEvent.ImageFileChanged -> {
                _completeProfileState.value =
                    _completeProfileState.value.copy(profileImage = event.image)
            }
            is CompleteProfileEvent.Submit -> {
                submitData()
            }
            is CompleteProfileEvent.SchoolChanged ->{
                submitPinCode()
            }
        }
    }

    private fun submitData() {
        val pinCodeResult = validatePinCode.execute(_completeProfileState.value.pinCode)
        val schoolResult = validateSchool.execute(_completeProfileState.value.schoolName)
        val passwordResult = validatePassword.execute(_completeProfileState.value.password)
        val confirmPasswordlResult =
            validateConfirmPassword.execute(_completeProfileState.value.confirmPassword,_completeProfileState.value.password)

        val hasError = listOf(
            pinCodeResult,
            schoolResult,
            passwordResult,
            confirmPasswordlResult
        ).any { !it.successful }

        if (hasError) {
            _completeProfileState.value = _completeProfileState.value.copy(
                pinCodeError = pinCodeResult.errorMessage,
                schoolError = schoolResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordlResult.errorMessage
            )
            Log.i("LoginViewModel", "profile : ${_completeProfileState.value}")

            viewModelScope.launch {
                validationEventChannel.send(
                    ValidationCompleteProfileEvent.Error(
                        _completeProfileState.value.pinCodeError,
                        _completeProfileState.value.schoolError,
                        _completeProfileState.value.passwordError,
                        _completeProfileState.value.confirmPasswordError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(
                ValidationCompleteProfileEvent.Success(
                    _completeProfileState.value.pinCode,
                    _completeProfileState.value.schoolCode,
                    _completeProfileState.value.schoolName,
                    _completeProfileState.value.password,
                    _completeProfileState.value.confirmPassword,
                    _completeProfileState.value.profileImage
                )
            )
        }
    }

    private fun submitPinCode() {
        val pinCodeResult = validatePinCode.execute(_completeProfileState.value.pinCode)

        val hasError = !pinCodeResult.successful

        Log.i("CompleteProfileModel", "pincodeResult: ${pinCodeResult}")

        if (hasError) {
            _completeProfileState.value = _completeProfileState.value.copy(
                pinCodeError = pinCodeResult.errorMessage
            )
            Log.i("CompleteProfileModel", "profile : ${_completeProfileState.value}")

            viewModelScope.launch {
                schoolValidationEventChannel.send(
                    ValidationSchoolNameEvent.Error(
                        _completeProfileState.value.pinCodeError
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            schoolValidationEventChannel.send(
                ValidationSchoolNameEvent.Success(
                    _completeProfileState.value.pinCode
                )
            )
        }
    }

    fun updateProfile(
        updateProfileFormRequest: UpdateProfileFormRequest
    ) = viewModelScope.launch {
        updateProfileUseCase.execute(updateProfileFormRequest).collect {
            _completeProfileStateFlow.value = it
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