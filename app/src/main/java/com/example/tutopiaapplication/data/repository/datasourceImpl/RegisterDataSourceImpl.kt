package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.classFeature.ClassesModel
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import retrofit2.Response

class RegisterDataSourceImpl(private val apiVersion2Service: ApiVersion2Service , private val apiVersion1Service: ApiVersion1Service) :
    RegisterDataSource {
    override suspend fun registerUser(registerRequestEntity: RegisterRequestEntity): Response<RegisterResponseEntity> {
           return apiVersion2Service.registerUser(registerRequestEntity)
    }

    override suspend fun getBoardsList(): Response<BoardsModel> {
        return apiVersion1Service.getBoardLists()
    }

    override suspend fun getClassLists(): Response<ClassesModel> {
        return apiVersion1Service.getClassLists()
    }
}