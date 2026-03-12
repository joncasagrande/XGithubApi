package com.joncasagrande.domain.mapper

import com.jonathan.data.model.Owner
import com.jonathan.data.model.Github
import com.jonathan.domain.mapper.GithubRepoMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubRepoMapperTest {

    lateinit var githubRepoMapper: GithubRepoMapper

    @Before
    fun setup() {
        githubRepoMapper = GithubRepoMapper()
    }

    @Test
    fun mapGitReposApiToGitRepos() {
        //given
        val githubRepos =
            listOf(
                Github(
                    owner = Owner(avatarUrl = "avatar_url"),
                    name = "repoRepo",
                    forksCount = 10,
                    watchers = 1100,
                    language = "kotlin"
                )
            )


        //when
        val dtos = githubRepoMapper.mapper(githubRepos)

        //then
        assertEquals(
            dtos.first().image,
            "avatar_url"
        )

        assertEquals(
            dtos.first().name,
            "repoRepo"
        )
    }
}