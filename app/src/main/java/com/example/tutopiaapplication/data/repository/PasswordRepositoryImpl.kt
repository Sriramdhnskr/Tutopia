package com.example.tutopiaapplication.data.repository

import android.util.Log
import com.example.tutopiaapplication.core.util.ErrorResponse
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.repository.datasource.PasswordDataSource
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.domain.model.Board
import com.example.tutopiaapplication.domain.model.ClassDetails
import com.example.tutopiaapplication.domain.repository.PasswordRepository
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class PasswordRepositoryImpl(private val passwordDataSource: PasswordDataSource) :  PasswordRepository{
    override suspend fun forgotPassword(otpRequestEntity: OtpRequestEntity): Flow<Resource<ForgotPasswordResponseEntity>> {
        return flow {
            try{
                emit(Resource.Loading())
                val response = passwordDataSource.forgotPassword(otpRequestEntity)
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!, message =response.body()!!.message ))
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

    override suspend fun resetPassword(resetPasswordRequestEntity: ResetPasswordRequestEntity): Flow<Resource<ResetPasswordResponseEntity>> {
        return flow {
            try{
                emit(Resource.Loading())
                val response = passwordDataSource.resetPassword(resetPasswordRequestEntity)
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!, message =response.body()!!.message ))
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