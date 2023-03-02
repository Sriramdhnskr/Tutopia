package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.classFeature.ClassesModel
import retrofit2.Response

interface RegisterDataSource {
    suspend fun registerUser(registerRequestEntity: RegisterRequestEntity) : Response<RegisterResponseEntity>
    suspend fun getBoardsList() : Response<BoardsModel>
    suspend fun getClassLists() : Response<ClassesModel>
}