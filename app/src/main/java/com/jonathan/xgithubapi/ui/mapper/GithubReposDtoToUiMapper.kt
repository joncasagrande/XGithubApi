package com.jonathan.xgithubapi.ui.mapper

import com.jonathan.domain.model.GithubReposDto
import com.jonathan.xgithubapi.ui.model.GithubUi

class GithubReposDtoToUiMapper {
    fun mapper(githubRepos: List<GithubReposDto>): List<GithubUi> {
        return githubRepos.map { repos ->
            GithubUi(
                name = repos.name,
                avatar = repos.image.orEmpty(),
                description = repos.description,
                stars = repos.stars,
                forks = repos.forks,
                lastUpdated = repos.lastUpdated,
                language = repos.lang,
                license = repos.license,
                ownerName = repos.ownerName,
                ownerInfo = repos.ownerInfo,
                fork = repos.fork
            )
        }
    }
}