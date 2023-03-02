package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.domain.model.Board
import com.example.tutopiaapplication.domain.model.ClassDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RegisterRepository {
    suspend fun registerUser(requestRequestEntity: RegisterRequestEntity) : Flow<Resource<RegisterResponseEntity>>
    suspend fun getBoardsList() : Flow<Resource<List<Board>>>
    suspend fun getClassLists(): Flow<Resource<List<ClassDetails>>>
}