package com.example.tutopiaapplication.data.repository.datasourceImpl

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolsModel
import com.example.tutopiaapplication.data.repository.datasource.UpdateProfileDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class UpdateProfileDataSourceImpl(private val apiVersion2Service: ApiVersion2Service, private val apiVersion1Service: ApiVersion1Service) : UpdateProfileDataSource {
    override suspend fun updateProfile(
        pinCode: RequestBody,
        profileImage: MultipartBody.Part?,
        password: RequestBody,
        confirmPassword: RequestBody,
        schoolName: RequestBody?,
        schoolCode: RequestBody?
    ): Response<UpdateProfileResponse> {
        return apiVersion2Service.updateProfile(pinCode = pinCode,profileImage, password = password,confirmPassword = confirmPassword, schoolName = schoolName, schoolCode = schoolCode)
    }

    override suspend fun getSchoolLists(): Response<SchoolsModel> {
        return apiVersion1Service.getSchoolLists()
    }
}