package com.jonathan.xgithubapi.ui.model

data class EventState(
    val idle: Boolean = false,
    val showLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val listDogUi: List<GithubUi>? = null,
    val showError: String? = null
)
