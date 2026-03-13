package com.jonathan.xgithubapi.ui.fragment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
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
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFragment(
    eventState: EventState?,
    clickListener: (GithubUi) -> Unit,
    loadNextPage: () -> Unit,
    onRefresh: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {
        snapshotFlow {
            val layoutInfo = gridState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItems = layoutInfo.totalItemsCount
            totalItems > 0 && lastVisibleIndex >= totalItems - 4
        }.distinctUntilChanged().collect { nearEnd ->
            val reposState = eventState as? EventState.Repos ?: return@collect
            if (nearEnd && !reposState.isLoadingNextPage) {
                loadNextPage.invoke()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { GitToolbar("Github Repositories", scrollBehavior) }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = (eventState == EventState.Loading),
            onRefresh = onRefresh,
            modifier = Modifier.padding(innerPadding)
        ) {
            when (val state = eventState) {
                is EventState.Repos -> {
                    val repoList = state.repositories.orEmpty()
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(repoList, key = { it.name + it.ownerName }) { repo ->
                            Card(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable(onClick = { clickListener.invoke(repo) })
                            ) {
                                GithubCard(repo)
                            }
                        }

                        if (state.isLoadingNextPage) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                LoadingComponent()
                            }
                        }

                        if (!state.nextPageError.isNullOrBlank()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                ErrorCompose(state.nextPageError)
                            }
                        }
                    }
                }

                is EventState.Error -> {
                    Column {
                        ErrorCompose(state.message)
                    }
                }

                is EventState.Empty -> {
                    Column {
                        EmptyListCompose(getString(LocalContext.current, R.string.empty_list))
                    }
                }

                is EventState.Loading -> {
                    Column {
                        LoadingComponent()
                    }
                }

                else -> {
                    Column {
                        ErrorCompose("Unknown state")
                    }
                }
            }
        }
    }
}