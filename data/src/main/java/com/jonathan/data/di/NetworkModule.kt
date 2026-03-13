package com.jonathan.data.di

import com.google.gson.FieldNamingPolicy
import com.jonathan.data.BuildConfig
import com.jonathan.data.api.GithubApi
import com.jonathan.data.api.GithubApiImpl
import com.jonathan.data.repository.GithubRepositoryImpl
import com.jonathan.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                    setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                }
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }

            defaultRequest {
                header("Accept", "application/vnd.github+json")
                header("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
                header("X-GitHub-Api-Version", "2026-03-10")
            }

        }
    }

    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): GithubApi {
        return GithubApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideRepository(dogApi: GithubApi): GithubRepository {
        return GithubRepositoryImpl(dogApi)
    }
}