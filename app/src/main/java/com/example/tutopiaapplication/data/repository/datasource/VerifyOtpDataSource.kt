package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import retrofit2.Response

interface VerifyOtpDataSource {
    suspend fun verifyOtpUser(requestOtpEntity: VerifyOtpRequestEntity) : Response<VerifyOtpResponseEntity>
}