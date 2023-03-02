package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateName {
    fun execute(name: String)  : ValidationResult {
        if(name.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your name"
            )
        }
        return ValidationResult(successful = true)
    }
}