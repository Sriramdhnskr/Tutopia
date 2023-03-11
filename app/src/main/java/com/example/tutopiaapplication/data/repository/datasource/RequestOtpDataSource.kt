package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import retrofit2.Response

interface RequestOtpDataSource {
    suspend fun requestOtpUser(otpRequestEntity: OtpRequestEntity) : Response<OtpResponseEntity>
}