package com.example.tutopiaapplication.domain.usecase

import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.updateProfile.school.SchoolDetails
import com.example.tutopiaapplication.domain.repository.UpdateProfileRepository
import kotlinx.coroutines.flow.Flow

class GetSchoolsListUseCase(private val repository: UpdateProfileRepository) {
    suspend fun execute() : Flow<Resource<List<SchoolDetails>>> {
        return repository.getSchoolLists()
    }
}