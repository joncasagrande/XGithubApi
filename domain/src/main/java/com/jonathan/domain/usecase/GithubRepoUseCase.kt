package com.jonathan.domain.usecase

import com.jonathan.data.repository.GithubRepository
import com.jonathan.data.utils.Resource
import com.jonathan.domain.mapper.GithubRepoMapper
import com.jonathan.domain.model.GithubReposDto
import javax.inject.Inject

class GithubRepoUseCase @Inject constructor(
    val repository: GithubRepository,
    val mapper: GithubRepoMapper
) {
    sealed class Event {
        data class Success(val listDogs: List<GithubReposDto>) : Event()
        data class Error(val error: String) : Event()
    }

    suspend fun getRepos(): Event {
        return when (val data = repository.getListRepo()) {
            is Resource.Success -> Event.Success(mapper.mapper(data.value))
            is Resource.Error -> Event.Error(data.error)
        }
    }
}