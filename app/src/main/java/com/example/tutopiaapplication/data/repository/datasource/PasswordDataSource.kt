package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import retrofit2.Response

interface PasswordDataSource {
    suspend fun forgotPassword(otpRequestEntity: OtpRequestEntity): Response<ForgotPasswordResponseEntity>
    suspend fun resetPassword(resetPasswordRequestEntity: ResetPasswordRequestEntity): Response<ResetPasswordResponseEntity>
}