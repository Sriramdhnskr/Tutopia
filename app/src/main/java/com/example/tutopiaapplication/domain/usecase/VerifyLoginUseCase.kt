package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.domain.repository.LoginRepository
import com.example.tutopiaapplication.domain.repository.OtpRepository
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

class VerifyLoginUseCase(private val repository: OtpRepository) {
    suspend fun execute(verifyLoginRequestEntity: VerifyLoginRequestEntity) : Flow<Resource<VerifyLoginResponseEntity>> {
        return repository.verifyLoginUser(verifyLoginRequestEntity)
    }
}