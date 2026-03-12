package com.jonathan.data.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed interface NetworkResult<out T : Any> {
    data class Success<out T : Any>(val body: T) : NetworkResult<T>
    data class Error<out T : Any>(val error: Exception) : NetworkResult<T>
}

suspend inline fun <reified T : Any> HttpResponse.toResult(): NetworkResult<T> {
    return when (status.value) {
        200 -> NetworkResult.Success(body())
        400 -> NetworkResult.Error(NetworkException("Check your credentials and try again!"))
        401 -> NetworkResult.Error(NetworkException("Authorization Failed! Try Logging In again."))
        404 -> NetworkResult.Error(NetworkException("Repo not found."))
        500, 503 -> NetworkResult.Error(NetworkException("Server Disruption! We are on fixing it."))
        504 -> NetworkResult.Error(NetworkException("Too much load at this time, try again later!"))
        else -> NetworkResult.Error(NetworkException("Something went wrong! Please try again or contact support."))
    }
}

class NetworkException(message: String) : Exception(message)
