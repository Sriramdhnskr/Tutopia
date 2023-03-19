package com.example.tutopiaapplication.data.repository

import com.example.tutopiaapplication.core.util.ErrorResponse
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.repository.datasource.LoginUserDataSource
import com.example.tutopiaapplication.domain.repository.LoginRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class LoginRepositoryImpl(var loginUserDataSource: LoginUserDataSource) : LoginRepository{
    override suspend fun loginUser(loginRequestEntity: LoginRequestEntity): Flow<Resource<LoginResponseEntity>> {
        return flow {
            try{
                emit(Resource.Loading())
                val response = loginUserDataSource.loginUser(loginRequestEntity)
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!,message = response.body()!!.message))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    when(response.code()){
                        412 -> {
                            emit( Resource.Error(message = "Precondition Failed", error = errorResponse.error))
                        }
                        else ->{
                            emit(Resource.Error(message = errorResponse.message, error = errorResponse.error))
                        }
                    }
                }
            } catch(e : Exception) {
                emit(Resource.Error(message = "Registration failed due to an error: ${e.message}"))
            }
        }
    }
}