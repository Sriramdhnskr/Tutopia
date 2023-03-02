package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateClass {
    fun execute(className : String)  : ValidationResult {
        if(className.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select the class"
            )
        }
        return ValidationResult(successful = true)
    }
}