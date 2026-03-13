package com.jonathan.xgithubapi.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jonathan.xgithubapi.R
import com.jonathan.xgithubapi.ui.model.GithubUi
import com.jonathan.xgithubapi.ui.theme.XGithubApiTheme


@Composable
fun GithubCard(githubUi: GithubUi, modifier: Modifier = Modifier) {
    val color = (if (githubUi.fork) Color(0xFF1EB980) else Color(0xFFFFFFFF))
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)


    ) {
        AsyncImage(
            model = githubUi.avatar,
            contentDescription = githubUi.name,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(120.dp)
        )
        Text(
            githubUi.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        Text(
            githubUi.ownerName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Text(
            githubUi.description ?: stringResource(R.string.empty_description),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
    }
}

@Preview(showBackground = true)
@Composable
fun GithubPreview() {
    XGithubApiTheme {
        GithubCard(
            GithubUi(
                name = "Jetpack Compose",
                description = "Jetpack Compose is Android's modern toolkit for building native UIs.",
                stars = 1234,
                forks = 567,
                lastUpdated = "2 days ago",
                language = "Kotlin",
                license = "MIT",
                avatar = "https://avatars.githubusercontent.com/u/27901?v=4",
                ownerName = "Google",
                ownerInfo = "owner info",
                fork = true
            )
        )
    }
}