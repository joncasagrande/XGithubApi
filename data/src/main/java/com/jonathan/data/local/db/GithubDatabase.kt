package com.jonathan.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonathan.data.local.dao.RepoDao
import com.jonathan.data.local.entity.RepoEntity

@Database(entities = [RepoEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}

