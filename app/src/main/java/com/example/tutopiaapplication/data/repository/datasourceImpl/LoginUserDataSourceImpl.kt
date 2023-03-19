package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.repository.datasource.LoginUserDataSource
import retrofit2.Response

class LoginUserDataSourceImpl(private val apiVersion2Service: ApiVersion2Service, private val apiVersion1Service: ApiVersion1Service) : LoginUserDataSource {
    override suspend fun loginUser(loginRequestEntity: LoginRequestEntity): Response<LoginResponseEntity> {
        return apiVersion1Service.loginUser(loginRequestEntity)
    }

    override suspend fun verifyLoginUser(verifyLoginRequestEntity: VerifyLoginRequestEntity): Response<VerifyLoginResponseEntity> {
        return apiVersion1Service.verifyLoginUser(verifyLoginRequestEntity)

    }
}