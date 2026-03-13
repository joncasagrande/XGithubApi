package com.jonathan.data.api

import com.jonathan.data.api.HttpRoutes.GITHUB_API
import com.jonathan.data.model.Github
import com.jonathan.data.utils.NetworkResult
import com.jonathan.data.utils.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class GithubApiImpl @Inject constructor(
    private val client: HttpClient
) : GithubApi {

    override suspend fun fetchRepos(amount: Int?, page: Int): NetworkResult<List<Github>> {
        return client.get {
            url(GITHUB_API)
            parameter("page", page)
            amount?.let { parameter("per_page", it) }
        }.toResult()
    }

}