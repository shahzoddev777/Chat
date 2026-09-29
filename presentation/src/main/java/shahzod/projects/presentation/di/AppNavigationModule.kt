package shahzod.projects.presentation.di

import shahzod.projects.presentation.util.navigation.AppNavigationDispatcher
import shahzod.projects.presentation.util.navigation.AppNavigationHandler
import shahzod.projects.presentation.util.navigation.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppNavigationModule {

    @Provides
    @Singleton
    fun provideAppNavigator(): AppNavigator = AppNavigationDispatcher

    @Provides
    @Singleton
    fun provideAppNavigationHandler(): AppNavigationHandler = AppNavigationDispatcher
}
