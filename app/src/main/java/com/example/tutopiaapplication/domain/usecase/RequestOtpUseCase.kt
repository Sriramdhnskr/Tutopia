package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.domain.repository.OtpRepository
import kotlinx.coroutines.flow.Flow

class RequestOtpUseCase(private val repository: OtpRepository) {
    suspend fun execute(otpRequestEntity: OtpRequestEntity) : Flow<Resource<OtpResponseEntity>> {
        return repository.requestOtpUser(otpRequestEntity)
    }
}