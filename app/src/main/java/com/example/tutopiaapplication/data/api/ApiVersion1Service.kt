package com.example.tutopiaapplication.data.api

import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.classFeature.ClassesModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiVersion1Service {
    @GET("board")
    suspend fun getBoardLists() : Response<BoardsModel>

    @GET("classes")
    suspend fun getClassLists() : Response<ClassesModel>
}