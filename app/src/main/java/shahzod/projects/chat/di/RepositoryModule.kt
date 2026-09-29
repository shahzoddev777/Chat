package shahzod.projects.chat.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shahzod.projects.data.repository.AuthRepositoryImpl
import shahzod.projects.domain.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @[Binds Provides]
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}