package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoginRepository {
    suspend fun loginUser(
        loginRequestEntity: LoginRequestEntity
    ): Flow<Resource<LoginResponseEntity>>
}