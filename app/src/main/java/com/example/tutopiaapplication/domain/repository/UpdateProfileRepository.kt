package com.example.tutopiaapplication.domain.repository

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolDetails
import kotlinx.coroutines.flow.Flow

interface UpdateProfileRepository {
    suspend fun updateProfile(
        updateProfileFormRequest: UpdateProfileFormRequest
    ): Flow<Resource<UpdateProfileResponse>>

    suspend fun getSchoolLists(): Flow<Resource<List<SchoolDetails>>>
}