package com.example.tutopiaapplication.presentation.di.core
import com.example.tutopiaapplication.data.repository.OtpRepositoryImpl
import com.example.tutopiaapplication.data.repository.RegisterRepositoryImpl
import com.example.tutopiaapplication.data.repository.UpdateProfileRepositoryImpl
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.data.repository.datasource.RequestOtpDataSource
import com.example.tutopiaapplication.data.repository.datasource.UpdateProfileDataSource
import com.example.tutopiaapplication.data.repository.datasource.VerifyOtpDataSource
import com.example.tutopiaapplication.domain.repository.OtpRepository
import com.example.tutopiaapplication.domain.repository.RegisterRepository
import com.example.tutopiaapplication.domain.repository.UpdateProfileRepository
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

    @Singleton
    @Provides
    fun provideOtpRepository(
        verifyOtpDataSource: VerifyOtpDataSource,
        requestOtpDataSource: RequestOtpDataSource
    ): OtpRepository{
        return OtpRepositoryImpl(verifyOtpDataSource,requestOtpDataSource)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileRepository(
       updateProfileDataSource: UpdateProfileDataSource
    ): UpdateProfileRepository{
        return UpdateProfileRepositoryImpl(updateProfileDataSource)
    }

}