package com.example.tutopiaapplication.data.api

import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.classFeature.ClassesModel
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolsModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiVersion1Service {
    @GET("board")
    suspend fun getBoardLists() : Response<BoardsModel>

    @GET("classes")
    suspend fun getClassLists() : Response<ClassesModel>

    @GET("auth/schools")
    suspend fun getSchoolLists() : Response<SchoolsModel>

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequestEntity: LoginRequestEntity): Response<LoginResponseEntity>

    @POST("auth/login/verify")
    suspend fun verifyLoginUser(@Body verifyLoginRequestEntity: VerifyLoginRequestEntity): Response<VerifyLoginResponseEntity>

}