package com.jonathan.xgithubapi.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jonathan.xgithubapi.ui.components.GitToolbar
import com.jonathan.xgithubapi.ui.components.InnerToolbar
import com.jonathan.xgithubapi.ui.theme.XGithubApiTheme
import org.junit.Rule
import org.junit.Test

class ToolBarComposeTest {
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
                GitToolbar("Git App", null)
            }
        }

        composeTestRule.onNodeWithText("Git App").assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun innerGithubToolBarTest() {
        // Start the app
        composeTestRule.setContent {
            XGithubApiTheme {
                InnerToolbar(title = "Repository name", {  },null)
            }
        }

        composeTestRule.onNodeWithText("Repository name").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }
}