package com.jonathan.data.di

import android.content.Context
import androidx.room.Room
import com.jonathan.data.local.dao.RepoDao
import com.jonathan.data.local.db.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            "github_cache.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(database: GithubDatabase): RepoDao {
        return database.repoDao()
    }
}

