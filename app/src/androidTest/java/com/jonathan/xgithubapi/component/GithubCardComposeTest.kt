package com.jonathan.xgithubapi.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jonathan.xgithubapi.ui.components.GithubCard
import com.jonathan.xgithubapi.ui.model.GithubUi
import com.jonathan.xgithubapi.ui.theme.XGithubApiTheme
import org.junit.Rule
import org.junit.Test

class GithubCardComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun githubToolBarTest() {
        // Start the app
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithText("Jetpack Compose").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Jetpack Compose").performClick()
    }
}