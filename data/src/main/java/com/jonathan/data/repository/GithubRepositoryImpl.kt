package com.jonathan.data.repository

import com.jonathan.data.api.GithubApi
import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult
import com.jonathan.data.utils.Resource
import javax.inject.Inject
import kotlin.collections.List

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : GithubRepository {

    override suspend fun getListRepo(): Resource<List<Github>> {
        return when (val data = api.fetchRepos(null)) {
            is NetworkResult.Success -> Resource.Success(data.body)
            is NetworkResult.Error -> Resource.Error(
                "api error",
                data.error
            )
        } as Resource<List<Github>>
    }
}