package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateSchool {
    fun execute(school: String)  : ValidationResult {
        if(school.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select school"
            )
        }
        return ValidationResult(successful = true)
    }
}