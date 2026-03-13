package com.jonathan.xgithubapi.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jonathan.xgithubapi.ui.components.LoadingComponent
import com.jonathan.xgithubapi.ui.theme.XGithubApiTheme
import org.junit.Rule
import org.junit.Test

class LoadingComposeTest {
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
                LoadingComponent(

                )
            }
        }

        composeTestRule.onNodeWithText("Wait…").assertIsDisplayed()
    }
}