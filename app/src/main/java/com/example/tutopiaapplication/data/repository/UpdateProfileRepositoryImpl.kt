package com.example.tutopiaapplication.data.repository

import android.util.Log
import com.example.tutopiaapplication.core.util.ErrorResponse
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolDetails
import com.example.tutopiaapplication.data.repository.datasource.UpdateProfileDataSource
import com.example.tutopiaapplication.domain.repository.UpdateProfileRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UpdateProfileRepositoryImpl(private val updateProfileDataSource: UpdateProfileDataSource) : UpdateProfileRepository{
    override suspend fun updateProfile(
      updateProfileFormRequest: UpdateProfileFormRequest
    ): Flow<Resource<UpdateProfileResponse>> {

        val requestBodyPincode = updateProfileFormRequest.pinCode.toRequestBody(MultipartBody.FORM)

        val requestImageFile = updateProfileFormRequest.profileImage?.asRequestBody("image/*".toMediaTypeOrNull())

        var imagePart : MultipartBody.Part? = null
        if(requestImageFile!=null)
        {
         imagePart = MultipartBody.Part.createFormData("profile_image",
             updateProfileFormRequest.profileImage.name, requestImageFile
            )
        }

        val requestBodyPassword = updateProfileFormRequest.password.toRequestBody(MultipartBody.FORM)
        val requestBodyConfirmPassword = updateProfileFormRequest.confirmPassword.toRequestBody(MultipartBody.FORM)
        val requestBodySchoolName = updateProfileFormRequest.schoolName?.toRequestBody(MultipartBody.FORM)
        val requestBodySchoolCode = updateProfileFormRequest.schoolCode?.toRequestBody(MultipartBody.FORM)

        return flow {
            try{
                emit(Resource.Loading())
                val response = updateProfileDataSource.updateProfile(pinCode = requestBodyPincode, profileImage = imagePart, password = requestBodyPassword,confirmPassword = requestBodyConfirmPassword,schoolName = requestBodySchoolName, schoolCode = requestBodySchoolCode)
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!, message =response.body()!!.message ))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    when(response.code()){
                        412 -> {
                            emit( Resource.Error(message = "Precondition Failed", error = errorResponse.error))
                        }
                        else ->{
                            emit(Resource.Error(message = errorResponse.message, error = errorResponse.error))
                        }
                    }
                }
            } catch(e : Exception) {
                emit(Resource.Error(message = "Registration failed due to an error: ${e.message}"))
            }
        }
    }

    override suspend fun getSchoolLists(): Flow<Resource<List<SchoolDetails>>> {
        return flow {
            emit(Resource.Loading())
            val response = updateProfileDataSource.getSchoolLists()
            Log.i("Response", "${response.body()}")
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.data.schools.map { it.toSchool() }
                Log.i("Data", "${data}")
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(message = response.body()!!.message))
            }
        }.catch { e ->
            emit(Resource.Error("Registration failed due to an error: ${e.message}"))
        }
    }
}