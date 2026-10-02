package shahzod.projects.chat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import shahzod.projects.core.network.api.AuthApi
import shahzod.projects.core.network.api.UsersApi

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create()

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()
}
