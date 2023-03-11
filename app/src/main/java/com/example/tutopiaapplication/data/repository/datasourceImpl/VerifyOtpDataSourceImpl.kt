package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.data.repository.datasource.VerifyOtpDataSource
import retrofit2.Response

class VerifyOtpDataSourceImpl(private val apiVersion2Service: ApiVersion2Service, private val apiVersion1Service: ApiVersion1Service) : VerifyOtpDataSource {
    override suspend fun verifyOtpUser(requestOtpEntity: VerifyOtpRequestEntity): Response<VerifyOtpResponseEntity> {
        return apiVersion2Service.verifyOtpUser(requestOtpEntity)
    }
}