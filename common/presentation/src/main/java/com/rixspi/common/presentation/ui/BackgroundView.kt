package com.rixspi.common.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rixspi.common.presentation.ui.galaxy.Galaxy

@Composable
fun BackgroundDayAndNightView(content: @Composable () -> Unit = {}) {
    Surface(color = Color.Black, modifier = Modifier.fillMaxWidth()) {
        Galaxy(modifier = Modifier.fillMaxSize())
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
fun BackgroundView() {
    BackgroundDayAndNightView()
}