package com.jonathan.data.repository

import com.jonathan.data.api.GithubApi
import com.jonathan.data.local.dao.RepoDao
import com.jonathan.data.model.Github
import com.jonathan.data.repository.mapper.toEntity
import com.jonathan.data.repository.mapper.toModel
import com.jonathan.data.utils.NetworkResult
import com.jonathan.data.utils.Resource
import javax.inject.Inject
import kotlin.collections.List

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val repoDao: RepoDao
) : GithubRepository {

    override suspend fun getListRepo(page: Int, perPage: Int): Resource<List<Github>> {
        return when (val data = api.fetchRepos(amount = perPage, page = page)) {
            is NetworkResult.Success -> {
                repoDao.clearAll()
                repoDao.insertAll(data.body.map { it.toEntity() })
                Resource.Success(data.body)
            }

            is NetworkResult.Error -> {
                val cachedRepos = repoDao.getAll().map { it.toModel() }
                if (cachedRepos.isNotEmpty()) {
                    Resource.Success(cachedRepos)
                } else {
                    Resource.Error("api error", data.error)
                }
            }
        } as Resource<List<Github>>
    }
}