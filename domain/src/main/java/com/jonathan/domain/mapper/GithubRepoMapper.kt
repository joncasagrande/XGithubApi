package com.jonathan.domain.mapper

import com.jonathan.data.model.Github
import com.jonathan.domain.model.GithubReposDto

class GithubRepoMapper {
    fun mapper(githubRepos: List<Github>?): List<GithubReposDto> {
        return githubRepos?.map { repos ->
            GithubReposDto(
                image = repos.owner?.avatarUrl,
                name = repos.name.orEmpty(),
                forks = repos.forksCount ?: 0,
                watchers = repos.watchers ?: 0,
                lang = repos.language ?: "N/A",
                description = repos.description,
                ownerName = repos.owner?.login.orEmpty(),
                ownerInfo = repos.owner?.htmlUrl.orEmpty(),
                stars = repos.stargazersCount ?: 0,
                license = repos.license?.name.orEmpty(),
                lastUpdated = repos.updatedAt.orEmpty(),
                fork = repos.fork ?: false
            )
        } ?: emptyList()
    }
}