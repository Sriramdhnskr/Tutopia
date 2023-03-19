package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface OtpRepository {
    suspend fun verifyOtpUser(requestOtpEntity: VerifyOtpRequestEntity): Flow<Resource<VerifyOtpResponseEntity>>
    suspend fun requestOtpUser(otpRequestEntity: OtpRequestEntity): Flow<Resource<OtpResponseEntity>>
    suspend fun verifyLoginUser(verifyLoginRequestEntity: VerifyLoginRequestEntity):
            Flow<Resource<VerifyLoginResponseEntity>>

}