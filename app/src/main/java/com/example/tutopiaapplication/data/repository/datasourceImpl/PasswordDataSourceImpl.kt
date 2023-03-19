package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.login.LoginResponseEntity
import com.example.tutopiaapplication.data.api.model.forgotPassword.ForgotPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordResponseEntity
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.repository.datasource.LoginUserDataSource
import com.example.tutopiaapplication.data.repository.datasource.PasswordDataSource
import retrofit2.Response

class PasswordDataSourceImpl(private val apiVersion2Service: ApiVersion2Service, private val apiVersion1Service: ApiVersion1Service) : PasswordDataSource {
    override suspend fun forgotPassword(otpRequestEntity: OtpRequestEntity): Response<ForgotPasswordResponseEntity> {
        return apiVersion2Service.forgotPassword(otpRequestEntity)

    }

    override suspend fun resetPassword(resetPasswordRequestEntity: ResetPasswordRequestEntity): Response<ResetPasswordResponseEntity> {
        return apiVersion2Service.resetPassword(resetPasswordRequestEntity)
    }

}