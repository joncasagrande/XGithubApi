package com.jonathan.xgithubapi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.jonathan.xgithubapi.R

@Composable
fun EmptyListCompose(
    error: String,
){
    Column (modifier = Modifier.
        padding(32.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Icon",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Text(text = error, Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun EmptyListComposePreview(){
    EmptyListCompose(getString(LocalContext.current, R.string.empty_list))
}