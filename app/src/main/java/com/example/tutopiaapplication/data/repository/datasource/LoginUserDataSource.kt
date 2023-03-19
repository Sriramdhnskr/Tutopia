package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import retrofit2.Response
import retrofit2.http.Body

interface LoginUserDataSource {
    suspend fun loginUser(loginRequestEntity: LoginRequestEntity): Response<LoginResponseEntity>
    suspend fun verifyLoginUser(verifyLoginRequestEntity: VerifyLoginRequestEntity): Response<VerifyLoginResponseEntity>
}