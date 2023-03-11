package com.example.tutopiaapplication.domain.usecase

import android.util.Log
import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateConfirmPassword {
    fun execute(confirmPassword: String, password: String)  : ValidationResult {
        Log.i("Password","${password} and ${confirmPassword}")
        if(confirmPassword.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your confirm password"
            )
        }
       if(confirmPassword != password)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Password and Confirm password not matched"
            )
        }
      /*  if(password.length<=6)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "password length must be atleast 6 characters"
            )
        }*/
        return ValidationResult(successful = true)
    }
}