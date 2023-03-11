package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidatePassword {
    fun execute(password: String)  : ValidationResult {
        if(password.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your password"
            )
        }
        if(password.length<=6)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "password length must be atleast 6 characters"
            )
        }
        return ValidationResult(successful = true)
    }
}