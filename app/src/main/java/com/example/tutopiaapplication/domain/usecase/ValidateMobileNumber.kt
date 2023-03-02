package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateMobileNumber {
    fun execute(mobileNumber: String)  : ValidationResult {
        if(mobileNumber.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your mobile no"
            )
        }
       else if(mobileNumber.length<10)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your 10 digit mobile no"
            )
        }
        return ValidationResult(successful = true)
    }
}