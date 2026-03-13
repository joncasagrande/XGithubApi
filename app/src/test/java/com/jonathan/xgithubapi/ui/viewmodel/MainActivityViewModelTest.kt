package com.jonathan.xgithubapi.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jonathan.domain.model.GithubReposDto
import com.jonathan.domain.usecase.GithubRepoUseCase
import com.jonathan.xgithubapi.ui.model.EventState
import com.jonathan.xgithubapi.ui.model.GithubUi
import com.jonathan.xgithubapi.ui.viewmodel.utils.mockkObserver
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainActivityViewModel

    @MockK
    lateinit var useCase: GithubRepoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainActivityViewModel(useCase)
    }

    @Test
    fun getListReposWithSuccess() {
        //given
        val githubReposDtos = listOf(
            GithubReposDto(
                name = "Jetpack Compose",
                description = "Jetpack Compose is Android's modern toolkit for building native UIs.",
                stars = 1234,
                forks = 567,
                lastUpdated = "2 days ago",
                lang = "Kotlin",
                license = "MIT",
                image = "https://avatars.githubusercontent.com/u/27901?v=4",
                ownerName = "Google",
                ownerInfo = "owner info",
                fork = true,
                watchers = 5
            )
        )
        val githubUis = listOf(
            GithubUi(
                name = "Jetpack Compose",
                description = "Jetpack Compose is Android's modern toolkit for building native UIs.",
                stars = 0,
                forks = 567,
                lastUpdated = "2 days ago",
                language = "Kotlin",
                license = "MIT",
                avatar = "https://avatars.githubusercontent.com/u/27901?v=4",
                ownerName = "Google",
                ownerInfo = "owner info",
                fork = true
            )
        )
        coEvery { useCase.getRepos(1, 20) } returns GithubRepoUseCase.Event.Success(
            githubReposDtos
        )
        val eventObserver = mockkObserver(viewModel.eventData)

        //when
        viewModel.loadRepos()
        //then
        coVerify {
            eventObserver.onChanged(EventState.Loading)
            eventObserver.onChanged(EventState.Repos(repositories = githubUis))
        }

    }

    @Test
    fun getListRepoWithEmpty() {
        //given
        val listDogBreedDto = emptyList<GithubReposDto>()
        coEvery { useCase.getRepos(1, 20) } returns GithubRepoUseCase.Event.Success(
            listDogBreedDto
        )
        val eventObserver = mockkObserver(viewModel.eventData)

        //when
        viewModel.loadRepos()
        //then
        coVerify {
            eventObserver.onChanged(EventState.Loading)
            eventObserver.onChanged(EventState.Empty)
        }

    }

    @Test
    fun getListReposWithError() {
        //given
        coEvery { useCase.getRepos(1, 20) } returns GithubRepoUseCase.Event.Error(
            "api error"
        )
        val eventObserver = mockkObserver(viewModel.eventData)

        //when
        viewModel.loadRepos()
        //then
        coVerify {
            eventObserver.onChanged(EventState.Loading)
            eventObserver.onChanged(EventState.Error(message = "api error"))
        }

    }
}