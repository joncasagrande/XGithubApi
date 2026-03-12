package com.jonathan.data.repository

import com.jonathan.data.model.Github
import com.jonathan.data.utils.Resource

const val AMOUNT = 10

interface GithubRepository {
    suspend fun getListRepo(): Resource<List<Github>>
}