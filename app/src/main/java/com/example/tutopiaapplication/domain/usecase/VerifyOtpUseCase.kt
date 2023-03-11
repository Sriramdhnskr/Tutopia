package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.domain.repository.OtpRepository
import kotlinx.coroutines.flow.Flow

class VerifyOtpUseCase(private val repository: OtpRepository) {
    suspend fun execute(otpRequestEntity: VerifyOtpRequestEntity) : Flow<Resource<VerifyOtpResponseEntity>> {
        return repository.verifyOtpUser(otpRequestEntity)
    }
}