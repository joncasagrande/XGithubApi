package com.jonathan.data.repository

import com.jonathan.data.api.GithubApi
import com.jonathan.data.local.dao.RepoDao
import com.jonathan.data.local.entity.RepoEntity
import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult
import com.jonathan.data.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubRepositoryImplTest {
    lateinit var githubRepository: GithubRepository

    @MockK
    lateinit var githubApi: GithubApi

    @MockK
    lateinit var repoDao: RepoDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        githubRepository = GithubRepositoryImpl(githubApi, repoDao)
    }

    @Test
    fun getDogListResourceSuccess() = runTest {
        //given
        val gitRepo = emptyList<Github>()

        coEvery { githubApi.fetchRepos(AMOUNT) } returns NetworkResult.Success(gitRepo)
        coEvery { repoDao.clearAll() } returns Unit
        coEvery { repoDao.insertAll(any()) } returns Unit

        //when
        val result = githubRepository.getListRepo() as Resource.Success<List<Github>>

        //then
        assertEquals(result.value.isEmpty(), true)
        coVerify(exactly = 1) { repoDao.clearAll() }
    }

    @Test
    fun getDogListResourceSuccessWithItem() = runTest {
        //given
        val gitRepo = listOf(Github(id = 1, name = "repo"))

        coEvery { githubApi.fetchRepos(AMOUNT) } returns NetworkResult.Success(gitRepo)
        coEvery { repoDao.clearAll() } returns Unit
        coEvery { repoDao.insertAll(any()) } returns Unit

        //when
        val result = githubRepository.getListRepo() as Resource.Success<List<Github>>

        //then
        assertEquals(result.value.isNotEmpty(), true)
        coVerify(exactly = 1) { repoDao.insertAll(any()) }
    }

    @Test
    fun getDogListResourceErrorUsesCache() = runTest {
        //given
        coEvery { githubApi.fetchRepos(AMOUNT) } returns NetworkResult.Error(Exception("error"))
        coEvery { repoDao.getAll() } returns listOf(
            RepoEntity(
                repoId = 1,
                name = "cached",
                description = "cached desc",
                forksCount = 1,
                watchers = 1,
                language = "Kotlin",
                stargazersCount = 1,
                updatedAt = "today",
                fork = false,
                ownerLogin = "owner",
                ownerAvatarUrl = null,
                ownerHtmlUrl = "ownerUrl",
                licenseName = "MIT"
            )
        )

        //when
        val result = githubRepository.getListRepo() as Resource.Success<List<Github>>

        //then
        assertEquals(result.value.first().name, "cached")
    }

    @Test
    fun getDogListResourceErrorWithoutCache() = runTest {
        //given
        coEvery { githubApi.fetchRepos(AMOUNT) } returns NetworkResult.Error(Exception("error"))
        coEvery { repoDao.getAll() } returns emptyList()

        //when
        val result = githubRepository.getListRepo() as Resource.Error<*>

        //then
        assertEquals(result.error, "api error")
    }
}