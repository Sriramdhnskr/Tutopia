package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PasswordRepository {
    suspend fun forgotPassword(otpRequestEntity: OtpRequestEntity): Flow<Resource<ForgotPasswordResponseEntity>>
    suspend fun resetPassword(resetPasswordRequestEntity: ResetPasswordRequestEntity): Flow<Resource<ResetPasswordResponseEntity>>
}