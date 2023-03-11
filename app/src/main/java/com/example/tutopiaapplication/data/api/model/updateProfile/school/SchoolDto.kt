package com.example.tutopiaapplication.data.api.model.updateProfile.school

data class SchoolDto(
    val pincode: String,
    val school_code: String,
    val school_id: String,
    val school_name: String
) {
    fun toSchool():  SchoolDetails{
        return SchoolDetails(
            pincode, school_code, school_id, school_name
        )
    }
}