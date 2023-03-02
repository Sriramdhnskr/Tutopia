package com.example.tutopiaapplication.data.api.model.register.board

import com.example.tutopiaapplication.domain.model.Board

data class BoardDto(
    val board_id: String,
    val board_name: String,
    val created_at: String,
    val is_deleted: String,
    val is_tutorial_available: String,
    val make_it_live: String,
    val updated_at: String
) {
    fun toBoard(): Board {
        return Board(
            boardId = board_id,
            boardName = board_name
        )
    }
}