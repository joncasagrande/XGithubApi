package com.jonathan.data.repository

import com.jonathan.data.model.Github
import com.jonathan.data.utils.Resource

const val AMOUNT = 10

interface GithubRepository {
    suspend fun getListRepo(page: Int = 1, perPage: Int = AMOUNT): Resource<List<Github>>
}