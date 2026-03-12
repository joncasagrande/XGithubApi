package com.jonathan.xgithubapi.ui.fragment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jonathan.xgithubapi.ui.components.InnerToolbar
import com.jonathan.xgithubapi.ui.model.GithubUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsFragment(
    navController: NavHostController?,
    githubUi: GithubUi
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            InnerToolbar(
                githubUi.name,
                { navController?.popBackStack() },
                scrollBehavior
            )
        }
    ) { innerPadding ->
        // Container for the entire detail page
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            // Owner Info (Avatar and Owner Name)
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(githubUi.avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Owned by: ${githubUi.ownerName}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // Repository Stats (Stars, Forks)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                StatItem(icon = Icons.Default.Star, count = githubUi.stars, label = "Stars")
                StatItem(icon = Icons.Default.Share, count = githubUi.forks, label = "Forks")
            }
            Spacer(modifier = Modifier.height(16.dp))


            // Repository Description
            Text(
                text = githubUi.description,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            // Last Updated and Language
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Last Updated: ${githubUi.lastUpdated}",
                    style = MaterialTheme.typography.bodyMedium
                )
                githubUi.language?.let {
                    Text(
                        text = "Language: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // License (optional)
            githubUi.license?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "License: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, count: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$count", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun RepoDetailPagePreview() {
    RepoDetailsFragment(
        null,
        GithubUi(
            name = "Jetpack Compose",
            description = "Jetpack Compose is Android's modern toolkit for building native UIs.",
            stars = 1234,
            forks = 567,
            lastUpdated = "2 days ago",
            language = "Kotlin",
            license = "MIT",
            avatar = "https://example.com/avatar.png",
            ownerName = "Google"
        )
    )
}
