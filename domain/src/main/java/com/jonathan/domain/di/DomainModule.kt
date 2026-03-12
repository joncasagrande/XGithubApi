package com.jonathan.domain.di

import com.jonathan.domain.mapper.GithubRepoMapper
import com.jonathan.domain.usecase.GithubRepoUseCase
import com.jonathan.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideMapper(): GithubRepoMapper {
        return GithubRepoMapper()
    }

    @Provides
    @Singleton
    fun provideGetBreedUseCase(
        repository: GithubRepository,
        mapper: GithubRepoMapper
    ): GithubRepoUseCase {
        return GithubRepoUseCase(repository, mapper)
    }

}