package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.domain.model.ValidationResult

class ValidateBoard {
    fun execute(board: String)  : ValidationResult {
        if(board.isEmpty())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select the board"
            )
        }
        return ValidationResult(successful = true)
    }
}