package com.example.tutopiaapplication.data.repository

import com.example.tutopiaapplication.core.util.ErrorResponse
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.data.repository.datasource.RequestOtpDataSource
import com.example.tutopiaapplication.data.repository.datasource.VerifyOtpDataSource
import com.example.tutopiaapplication.domain.repository.OtpRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OtpRepositoryImpl(private val verifyOtpDataSource: VerifyOtpDataSource, private val requestOtpDataSource: RequestOtpDataSource) : OtpRepository {
    override suspend fun verifyOtpUser(requestOtpEntity: VerifyOtpRequestEntity): Flow<Resource<VerifyOtpResponseEntity>> {
        return flow {
            try{
                emit(Resource.Loading())
                val response = verifyOtpDataSource.verifyOtpUser(requestOtpEntity)
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!))
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

    override suspend fun requestOtpUser(otpRequestEntity: OtpRequestEntity): Flow<Resource<OtpResponseEntity>> {
        return flow {
            try{
                emit(Resource.Loading())
                val response = requestOtpDataSource.requestOtpUser(otpRequestEntity)
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