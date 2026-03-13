package com.jonathan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jonathan.data.local.entity.RepoEntity

@Dao
interface RepoDao {

    @Query("SELECT * FROM repos ORDER BY repo_id ASC")
    suspend fun getAll(): List<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("DELETE FROM repos")
    suspend fun clearAll()
}

