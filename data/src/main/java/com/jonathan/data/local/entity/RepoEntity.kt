package com.jonathan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val repoId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int,
    @ColumnInfo(name = "watchers")
    val watchers: Int,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String?,
    @ColumnInfo(name = "fork")
    val fork: Boolean,
    @ColumnInfo(name = "owner_login")
    val ownerLogin: String,
    @ColumnInfo(name = "owner_avatar_url")
    val ownerAvatarUrl: String?,
    @ColumnInfo(name = "owner_html_url")
    val ownerHtmlUrl: String,
    @ColumnInfo(name = "license_name")
    val licenseName: String?
)

