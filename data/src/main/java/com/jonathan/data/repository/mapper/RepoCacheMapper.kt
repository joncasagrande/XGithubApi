package com.jonathan.data.repository.mapper

import com.jonathan.data.local.entity.RepoEntity
import com.jonathan.data.model.Github
import com.jonathan.data.model.License
import com.jonathan.data.model.Owner

fun Github.toEntity(): RepoEntity {
    return RepoEntity(
        repoId = id ?: 0,
        name = name.orEmpty(),
        description = description,
        forksCount = forksCount ?: 0,
        watchers = watchers ?: 0,
        language = language,
        stargazersCount = stargazersCount ?: 0,
        updatedAt = updatedAt,
        fork = fork ?: false,
        ownerLogin = owner?.login.orEmpty(),
        ownerAvatarUrl = owner?.avatarUrl,
        ownerHtmlUrl = owner?.htmlUrl.orEmpty(),
        licenseName = license?.name
    )
}

fun RepoEntity.toModel(): Github {
    return Github(
        id = repoId,
        name = name,
        description = description,
        forksCount = forksCount,
        watchers = watchers,
        language = language,
        stargazersCount = stargazersCount,
        updatedAt = updatedAt,
        fork = fork,
        owner = Owner(
            login = ownerLogin,
            avatarUrl = ownerAvatarUrl,
            htmlUrl = ownerHtmlUrl
        ),
        license = License(name = licenseName)
    )
}

