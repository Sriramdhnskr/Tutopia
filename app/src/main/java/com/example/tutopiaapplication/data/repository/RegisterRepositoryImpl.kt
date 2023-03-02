package com.example.tutopiaapplication.data.repository

import android.util.Log
import com.example.tutopiaapplication.core.util.ErrorResponse
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterResponseEntity
import com.example.tutopiaapplication.data.api.model.register.board.BoardsModel
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.domain.model.Board
import com.example.tutopiaapplication.domain.model.ClassDetails
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterRepositoryImpl(private val registerDataSource: RegisterDataSource) :  RegisterRepository{
    override suspend fun registerUser(requestRequestEntity: RegisterRequestEntity): Flow<Resource<RegisterResponseEntity>> {
        return flow {
            try{
            emit(Resource.Loading())
            val response = registerDataSource.registerUser(requestRequestEntity)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
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

    override suspend fun getBoardsList(): Flow<Resource<List<Board>>> {
        return flow {
            emit(Resource.Loading())

            val response = registerDataSource.getBoardsList()
            Log.i("Response","${response.body()}")
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.data.boards.map { it.toBoard() }
                Log.i("Data","${data}")
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(message = response.body()!!.message))
            }
        }.catch { e ->
            emit(Resource.Error("Registration failed due to an error: ${e.message}"))
        }
    }

    override suspend fun getClassLists(): Flow<Resource<List<ClassDetails>>> {
        return flow {
            emit(Resource.Loading())
            val response = registerDataSource.getClassLists()
            Log.i("Response","${response.body()}")
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.data.classes.map { it.toClassDetails() }
                Log.i("Data","${data}")
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(message = response.body()!!.message))
            }
        }.catch { e ->
            emit(Resource.Error("Registration failed due to an error: ${e.message}"))
        }
    }
}