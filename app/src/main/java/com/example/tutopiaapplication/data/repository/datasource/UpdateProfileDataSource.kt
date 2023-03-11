package com.example.tutopiaapplication.data.repository.datasource

import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolsModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface UpdateProfileDataSource {
    suspend fun updateProfile(
        pinCode: RequestBody,
        profileImage: MultipartBody.Part?=null,
        password: RequestBody,
        confirmPassword: RequestBody,
        schoolName: RequestBody? = null,
        schoolCode: RequestBody? = null
    ): Response<UpdateProfileResponse>

    suspend fun getSchoolLists(): Response<SchoolsModel>
}