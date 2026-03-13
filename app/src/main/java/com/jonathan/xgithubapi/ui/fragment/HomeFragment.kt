package com.jonathan.xgithubapi.ui.fragment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.jonathan.xgithubapi.R
import com.jonathan.xgithubapi.ui.components.EmptyListCompose
import com.jonathan.xgithubapi.ui.components.ErrorCompose
import com.jonathan.xgithubapi.ui.components.GitToolbar
import com.jonathan.xgithubapi.ui.components.GithubCard
import com.jonathan.xgithubapi.ui.components.LoadingComponent
import com.jonathan.xgithubapi.ui.model.EventState
import com.jonathan.xgithubapi.ui.model.GithubUi
import com.jonathan.xgithubapi.ui.viewmodel.MainActivityViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFragment(
    viewModel: MainActivityViewModel,
    clickListener: (GithubUi) -> Unit,
) {
    val eventState = viewModel.eventData.observeAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    LaunchedEffect(key1 = Unit) {
        viewModel.loadRepos()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { GitToolbar("Github Repositories", scrollBehavior) }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = (eventState.value == EventState.Loading),
            onRefresh = {
                viewModel.loadRepos()
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (eventState.value) {
                is EventState.Repos -> {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            val dogList: List<GithubUi> =
                                (eventState.value as EventState.Repos).repositories ?: emptyList()
                            items(dogList.size) { index ->
                                dogList.let {
                                    Card(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .clickable(
                                                onClick = { clickListener.invoke(it[index]) },
                                            )
                                    ) {
                                        GithubCard(it[index])
                                    }
                                }
                            }
                        }
                    }
                }

                is EventState.Error -> {
                    Column() {
                        ErrorCompose((eventState.value as EventState.Error).message)
                    }
                }

                is EventState.Empty -> {
                    Column() {
                        EmptyListCompose(getString(LocalContext.current, R.string.empty_list))
                    }
                }


                is EventState.Loading -> {
                    Column() {
                        LoadingComponent()
                    }
                }

                else -> {
                    Column() {
                        ErrorCompose("Unknown state")
                    }
                }
            }
        }
    }
}