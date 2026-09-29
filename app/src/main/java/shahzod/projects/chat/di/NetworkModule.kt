package shahzod.projects.chat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shahzod.projects.core.network.interceptor.TokenInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://relay.zokirov-mob-dev.uz/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}