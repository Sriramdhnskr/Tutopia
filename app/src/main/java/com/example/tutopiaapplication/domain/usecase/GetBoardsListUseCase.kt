package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.domain.model.Board
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class GetBoardsListUseCase(private val repository: RegisterRepository) {
    suspend fun execute() : Flow<Resource<List<Board>>> {
        return repository.getBoardsList()
    }
}