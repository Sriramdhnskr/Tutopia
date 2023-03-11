package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileResponse
import com.example.tutopiaapplication.domain.repository.UpdateProfileRepository
import kotlinx.coroutines.flow.Flow

class UpdateProfileUseCase(private val repository: UpdateProfileRepository) {
    suspend fun execute(updateProfileFormRequest: UpdateProfileFormRequest) : Flow<Resource<UpdateProfileResponse>> {
        return repository.updateProfile(updateProfileFormRequest)
    }
}