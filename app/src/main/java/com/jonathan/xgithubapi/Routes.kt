package com.jonathan.xgithubapi

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object ReposDescription : Routes("respo_description")
}