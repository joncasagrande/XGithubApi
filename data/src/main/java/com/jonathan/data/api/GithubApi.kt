package com.jonathan.data.api

import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult

interface GithubApi {
    suspend fun fetchRepos(amount: Int?, page: Int = 1): NetworkResult<List<Github>>
}