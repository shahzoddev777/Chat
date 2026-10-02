package shahzod.projects.chat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shahzod.projects.chat.presentation.util.navigation.AppNavigationDispatcher
import shahzod.projects.chat.presentation.util.navigation.AppNavigationHandler
import shahzod.projects.chat.presentation.util.navigation.AppNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppNavigationModule {

    @Provides
    @Singleton
    fun provideAppNavigator(): AppNavigator = AppNavigationDispatcher

    @Provides
    @Singleton
    fun provideAppNavigationHandler(): AppNavigationHandler = AppNavigationDispatcher
}