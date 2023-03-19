package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.domain.repository.LoginRepository
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class LoginUserUseCase(private val repository: LoginRepository) {
    suspend fun execute(loginRequestEntity: LoginRequestEntity) : Flow<Resource<LoginResponseEntity>> {
        return repository.loginUser(loginRequestEntity)
    }
}