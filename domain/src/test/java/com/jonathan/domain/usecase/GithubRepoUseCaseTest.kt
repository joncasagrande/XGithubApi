package com.jonathan.domain.usecase

import com.jonathan.data.model.Github
import com.jonathan.data.model.Owner
import com.jonathan.data.repository.AMOUNT
import com.jonathan.data.repository.GithubRepository
import com.jonathan.data.utils.Resource
import com.jonathan.domain.mapper.GithubRepoMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubRepoUseCaseTest {

    lateinit var dogBreedsUseCase: GithubRepoUseCase

    lateinit var mapper: GithubRepoMapper

    @MockK
    lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapper = GithubRepoMapper()
        dogBreedsUseCase = GithubRepoUseCase(repository, mapper)
    }

    @Test
    fun executeUseCaseWithSuccess() = runTest {
        //given
        val repos =
            listOf(
                Github(
                    owner = Owner(avatarUrl = "avatar_url"),
                    name = "repoRepo",
                    forksCount = 10,
                    watchers = 1100,
                    language = "kotlin"
                )
            )

        coEvery { repository.getListRepo(1, AMOUNT) } returns Resource.Success(repos)

        //when
        val success = dogBreedsUseCase.getRepos() as GithubRepoUseCase.Event.Success

        //then
        assertEquals(
            success.reposDtos.first().image,
            "avatar_url"
        )

        assertEquals(
            success.reposDtos.first().name,
            "repoRepo"
        )
    }

    @Test
    fun executeUseCaseWithError() = runTest {
        //given
        coEvery { repository.getListRepo(1, AMOUNT) } returns Resource.Error("error", null)

        //when
        val success = dogBreedsUseCase.getRepos() as GithubRepoUseCase.Event.Error

        //then
        assertEquals(
            success.error,
            "error"
        )
    }

}