package com.example.tutopiaapplication.data.api

import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpResponseEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpResponseEntity
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiVersion2Service {

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequestEntity: RegisterRequestEntity): Response<RegisterResponseEntity>

    @POST("auth/verify")
    suspend fun verifyOtpUser(@Body requestOtpEntity: VerifyOtpRequestEntity): Response<VerifyOtpResponseEntity>

    @POST("auth/otp")
    suspend fun requestOtpUser(@Body otpRequestEntity: OtpRequestEntity): Response<OtpResponseEntity>

    @Multipart
    @POST("profile/update")
    suspend fun updateProfile(
        @Part("pincode") pinCode: RequestBody,
        @Part image: MultipartBody.Part?= null,
        @Part("password") password: RequestBody,
        @Part("referralcode") referralCode: RequestBody?=null,
        @Part("confirm_password") confirmPassword : RequestBody,
        @Part("school_name") schoolName: RequestBody?= null,
        @Part("school_code") schoolCode: RequestBody?= null,
    ): Response<UpdateProfileResponse>
}