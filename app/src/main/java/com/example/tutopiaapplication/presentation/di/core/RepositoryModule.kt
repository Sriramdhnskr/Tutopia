package com.example.tutopiaapplication.presentation.di.core
import com.example.tutopiaapplication.data.repository.RegisterRepositoryImpl
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    //Register Repository
    @Singleton
    @Provides
    fun provideRegisterRepository(
      registerDataSource: RegisterDataSource
    ): RegisterRepository{
        return RegisterRepositoryImpl(registerDataSource)
    }

}