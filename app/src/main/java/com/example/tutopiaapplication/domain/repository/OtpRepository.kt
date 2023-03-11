package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import kotlinx.coroutines.flow.Flow

interface OtpRepository {
    suspend fun verifyOtpUser(requestOtpEntity: VerifyOtpRequestEntity) : Flow<Resource<VerifyOtpResponseEntity>>
    suspend fun requestOtpUser(otpRequestEntity: OtpRequestEntity) : Flow<Resource<OtpResponseEntity>>
}