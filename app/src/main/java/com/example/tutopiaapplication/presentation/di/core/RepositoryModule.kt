package com.example.tutopiaapplication.presentation.di.core
import com.example.tutopiaapplication.data.repository.*
import com.example.tutopiaapplication.data.repository.datasource.*
import com.example.tutopiaapplication.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.math.log

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
        requestOtpDataSource: RequestOtpDataSource,
        loginUserDataSource: LoginUserDataSource
    ): OtpRepository{
        return OtpRepositoryImpl(verifyOtpDataSource,requestOtpDataSource,loginUserDataSource)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileRepository(
       updateProfileDataSource: UpdateProfileDataSource
    ): UpdateProfileRepository{
        return UpdateProfileRepositoryImpl(updateProfileDataSource)
    }

    @Singleton
    @Provides
    fun providePasswordRepository(
        passwordDataSource: PasswordDataSource
    ): PasswordRepository{
        return PasswordRepositoryImpl(passwordDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
       loginUserDataSource: LoginUserDataSource
    ): LoginRepository{
        return LoginRepositoryImpl(loginUserDataSource)
    }

}