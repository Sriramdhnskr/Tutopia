package com.example.tutopiaapplication.domain.model

data class ValidationResult(
    val successful : Boolean,
    val errorMessage : String? = null
)