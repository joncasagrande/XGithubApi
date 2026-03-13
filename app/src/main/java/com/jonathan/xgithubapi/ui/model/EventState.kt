package com.jonathan.xgithubapi.ui.model


sealed interface EventState {
    object Idle : EventState
    object Loading : EventState
    object Empty : EventState

    data class Repos(
        val repositories: List<GithubUi>?,
        val isLoadingNextPage: Boolean = false,
        val nextPageError: String? = null
    ) : EventState

    data class Error(val message: String) : EventState
}