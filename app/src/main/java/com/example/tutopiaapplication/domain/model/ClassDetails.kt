package com.example.tutopiaapplication.domain.model

data class ClassDetails(val boardId : String,val classId : String, val className : String,val romanLetter : String)
{
    fun toMappedItem(): MappedItem {
        return MappedItem(
            id = classId,
            name = romanLetter
        )
    }
}