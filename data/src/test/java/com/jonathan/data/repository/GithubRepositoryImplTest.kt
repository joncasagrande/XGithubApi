package com.jonathan.data.repository

import com.jonathan.data.api.GithubApi
import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult
import com.jonathan.data.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubRepositoryImplTest {
    lateinit var githubRepository: GithubRepository

    @MockK
    lateinit var githubApi: GithubApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        githubRepository = GithubRepositoryImpl(githubApi)
    }

    @Test
    fun getDogListResourceSuccess() = runTest {
        //given
        val gitRepo = emptyList<Github>()


        coEvery { githubApi.fetchRepos(null) } returns NetworkResult.Success(gitRepo)

        //when
        val result = githubRepository.getListRepo() as Resource.Success<List<Github>>

        //then
        assertEquals(
            result.value.isEmpty(),
            true
        )
    }

    @Test
    fun getDogListResourceSuccessWithItem() = runTest {
        //given
        val gitRepo = listOf(Github())

         coEvery { githubApi.fetchRepos(null) } returns NetworkResult.Success(gitRepo)

        //when
        val result = githubRepository.getListRepo() as Resource.Success<List<Github>>

        //then
        assertEquals(
            result.value.isNotEmpty(),
            true
        )
    }

    @Test
    fun getDogListResourceError() = runTest {
        //given
        coEvery { githubApi.fetchRepos(null) } returns NetworkResult.Error(Exception("error"))

        //when
        val result = githubRepository.getListRepo() as Resource.Error<*>

        //then
        assertEquals(
            result.error.isNotEmpty(),
            true
        )
        assertEquals(
            result.error,
            "api error"
        )
    }
}