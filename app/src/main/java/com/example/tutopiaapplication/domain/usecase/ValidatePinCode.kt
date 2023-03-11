package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidatePinCode {
    fun execute(pinCode: String)  : ValidationResult {
        if(pinCode.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter school pincode"
            )
        }
        if(pinCode.length<6)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter 6 digit valid pincode"
            )
        }
        if(!pinCode.startsWith("7"))
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter pincode which starting with 7"
            )
        }
        return ValidationResult(successful = true)
    }
}