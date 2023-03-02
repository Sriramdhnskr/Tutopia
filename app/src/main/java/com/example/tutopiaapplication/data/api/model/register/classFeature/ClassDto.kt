package com.example.tutopiaapplication.data.api.model.register.classFeature

import com.example.tutopiaapplication.domain.model.ClassDetails

data class ClassDto(
    val academic_id: String,
    val board_id: String,
    val can_ask_question: String,
    val class_id: String,
    val class_image: ClassImage,
    val class_name: String,
    val created_at: String,
    val is_active: String,
    val is_deleted: String,
    val preparatory_exam: String,
    val roman_letter: String,
    val updated_at: String
) {
    fun toClassDetails(): ClassDetails {
        return ClassDetails(
            boardId = board_id,
            classId = class_id,
            className = class_name,
            romanLetter = roman_letter
        )
    }
}