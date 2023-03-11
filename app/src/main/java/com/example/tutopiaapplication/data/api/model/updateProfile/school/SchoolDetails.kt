package com.example.tutopiaapplication.data.api.model.updateProfile.school

import com.example.tutopiaapplication.domain.model.Board
import com.example.tutopiaapplication.domain.model.SchoolMappedItem

data class SchoolDetails(
    val pincode: String,
    val school_code: String,
    val school_id: String,
    val school_name: String
){
    fun toSchool(): SchoolMappedItem {
    return SchoolMappedItem(
        id = school_id,
        name = school_name,
        code = school_code
    )
}
}