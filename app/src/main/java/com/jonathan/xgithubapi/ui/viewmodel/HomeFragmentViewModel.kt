package com.jonathan.xgithubapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathan.domain.usecase.GithubRepoUseCase
import com.jonathan.xgithubapi.ui.mapper.GithubReposDtoToUiMapper
import com.jonathan.xgithubapi.ui.model.EventState
import com.jonathan.xgithubapi.ui.model.GithubUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: GithubRepoUseCase,
    private val mapper: GithubReposDtoToUiMapper
) : ViewModel() {

    private val _eventData = MutableLiveData<EventState>()
    val eventData: LiveData<EventState> = _eventData

    private var currentPage = 1
    private var hasMorePages = true
    private var isLoadingPage = false
    private val currentRepos = mutableListOf<GithubUi>()

    fun loadRepos() {
        if (isLoadingPage) return

        isLoadingPage = true
        currentPage = 1
        hasMorePages = true
        currentRepos.clear()
        _eventData.value = EventState.Loading

        viewModelScope.launch {
            when (val result = useCase.getRepos(page = currentPage, perPage = PAGE_SIZE)) {
                is GithubRepoUseCase.Event.Success -> {
                    val firstPage = mapper.mapper(result.reposDtos)
                    currentRepos.addAll(firstPage)
                    hasMorePages = firstPage.size >= PAGE_SIZE
                    _eventData.value = if (currentRepos.isEmpty()) {
                        EventState.Empty
                    } else {
                        EventState.Repos(repositories = currentRepos.toList())
                    }
                }

                is GithubRepoUseCase.Event.Error -> {
                    _eventData.value = EventState.Error(message = result.error)
                }
            }
            isLoadingPage = false
        }
    }

    fun loadNextPage() {
        if (isLoadingPage || !hasMorePages || currentRepos.isEmpty()) return

        isLoadingPage = true
        _eventData.value = EventState.Repos(
            repositories = currentRepos.toList(),
            isLoadingNextPage = true,
            nextPageError = null
        )

        viewModelScope.launch {
            val nextPage = currentPage + 1
            when (val result = useCase.getRepos(page = nextPage, perPage = PAGE_SIZE)) {
                is GithubRepoUseCase.Event.Success -> {
                    val nextRepos = mapper.mapper(result.reposDtos)
                    if (nextRepos.isNotEmpty()) {
                        currentPage = nextPage
                        currentRepos.addAll(nextRepos)
                    }
                    hasMorePages = nextRepos.size >= PAGE_SIZE
                    _eventData.value = EventState.Repos(repositories = currentRepos.toList())
                }

                is GithubRepoUseCase.Event.Error -> {
                    _eventData.value = EventState.Repos(
                        repositories = currentRepos.toList(),
                        isLoadingNextPage = false,
                        nextPageError = result.error
                    )
                }
            }
            isLoadingPage = false
        }
    }
}