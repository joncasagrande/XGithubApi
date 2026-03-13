package com.jonathan.xgithubapi.ui.mapper

import com.jonathan.domain.model.GithubReposDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubReposDtoToUiMapperTest {

    lateinit var mapper: GithubReposDtoToUiMapper

    @Before
    fun setup() {
        mapper = GithubReposDtoToUiMapper()
    }

    @Test
    fun mapGitReposApiToGitRepos() {
        //given
        val githubRepos =
            listOf(
                GithubReposDto(
                    name = "repoRepo",
                    forks = 10,
                    watchers = 1100,
                    lang = "kotlin",
                    description = "description",
                    stars = 100,
                    lastUpdated = "lastUpdated",
                    license = "license",
                    ownerName = "ownerName",
                    ownerInfo = "ownerInfo",
                    fork = false,
                    image = "avatar_url"
                )
            )


        //when
        val gitHubUi = mapper.mapper(githubRepos)

        //then
        assertEquals(
            gitHubUi.first().avatar,
            "avatar_url"
        )

        assertEquals(
            gitHubUi.first().name,
            "repoRepo"
        )
    }
}