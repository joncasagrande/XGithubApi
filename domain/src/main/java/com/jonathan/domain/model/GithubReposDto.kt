package com.jonathan.domain.model

data class GithubReposDto(
    val image: String?,
    val name: String,
    val forks: Int,
    val watchers: Int,
    val lang: String,
    val description: String?,
    val ownerName: String,
    val ownerInfo: String,
    val stars: Int,
    val license: String,
    val lastUpdated: String,
    val fork: Boolean
)