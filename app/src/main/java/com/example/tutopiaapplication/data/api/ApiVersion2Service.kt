package com.example.tutopiaapplication.data.api

import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiVersion2Service {

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequestEntity: RegisterRequestEntity): Response<RegisterResponseEntity>
}