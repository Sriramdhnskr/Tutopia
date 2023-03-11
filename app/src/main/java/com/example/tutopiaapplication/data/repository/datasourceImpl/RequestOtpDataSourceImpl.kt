package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.repository.datasource.RequestOtpDataSource
import retrofit2.Response

class RequestOtpDataSourceImpl(private val apiVersion2Service: ApiVersion2Service) : RequestOtpDataSource {
    override suspend fun requestOtpUser(otpRequestEntity: OtpRequestEntity): Response<OtpResponseEntity> {
        return apiVersion2Service.requestOtpUser(otpRequestEntity)
    }
}