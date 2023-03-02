package com.example.tutopiaapplication.presentation.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _isLoading : MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)

    var isLoading : LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }

}