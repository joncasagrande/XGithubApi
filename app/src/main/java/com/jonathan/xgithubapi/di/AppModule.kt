package com.jonathan.xgithubapi.di

import com.jonathan.xgithubapi.ui.mapper.GithubReposDtoToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMapper(): GithubReposDtoToUiMapper {
        return GithubReposDtoToUiMapper()
    }

}