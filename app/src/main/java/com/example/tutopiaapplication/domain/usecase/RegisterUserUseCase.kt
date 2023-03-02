package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(private val repository: RegisterRepository) {
    suspend fun execute(requestRegisterEntity: RegisterRequestEntity) : Flow<Resource<RegisterResponseEntity>> {
        return repository.registerUser(requestRegisterEntity)
    }
}