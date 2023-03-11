package com.example.tutopiaapplication.data.api

import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.classFeature.ClassesModel
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolsModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiVersion1Service {
    @GET("board")
    suspend fun getBoardLists() : Response<BoardsModel>

    @GET("classes")
    suspend fun getClassLists() : Response<ClassesModel>

    @GET("auth/schools")
    suspend fun getSchoolLists() : Response<SchoolsModel>
}