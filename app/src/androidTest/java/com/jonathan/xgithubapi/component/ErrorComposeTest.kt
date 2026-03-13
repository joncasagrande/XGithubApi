package com.jonathan.xgithubapi.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.jonathan.xgithubapi.ui.components.ErrorCompose
import com.jonathan.xgithubapi.ui.theme.XGithubApiTheme
import org.junit.Rule
import org.junit.Test

class ErrorComposeTest {
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
                ErrorCompose(
                    "Repo Not Found"
                )
            }
        }

        composeTestRule.onNodeWithText("Repo Not Found").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Error").assertIsDisplayed()
    }
}