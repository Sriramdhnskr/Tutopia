package com.example.tutopiaapplication.domain.model

data class Board(val boardId : String, val boardName : String)
{
    fun toMappedItem(): MappedItem {
        return MappedItem(
            id = boardId,
            name = boardName
        )
    }
}