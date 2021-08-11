package com.rixspi.common.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import kotlin.concurrent.timer
import kotlin.random.Random

@Composable
fun BackgroundDayAndNightView(content: @Composable () -> Unit = {}) {
    var time by remember { mutableStateOf(0) }
    val routine = timer(initialDelay = 1000, period = 1000) { time++ }
    Surface(color = Color.Cyan, modifier = Modifier.fillMaxWidth()) {

        BoxWithConstraints {
            val starsInARow = 3
            val rows = 3

            Canvas(modifier = Modifier.fillMaxSize()) {
                val spaceBetweenStars = maxWidth.toPx() / starsInARow
                val spaceBetweenRows = maxHeight.toPx() / rows
                val stars = 3
                val minimumFractionOfSpaceBetween = 3.0
                (0..rows).forEach { row ->
                    (0..stars).forEach { column ->
                        val y = spaceBetweenRows * row + Random.nextDouble(
                            -spaceBetweenRows / minimumFractionOfSpaceBetween,
                            spaceBetweenRows / minimumFractionOfSpaceBetween
                        )

                        val x = spaceBetweenStars * column + Random.nextDouble(
                            -spaceBetweenStars / minimumFractionOfSpaceBetween,
                            spaceBetweenStars / minimumFractionOfSpaceBetween
                        )

                        drawStar(Offset(y = y.toFloat() * time.toFloat(), x = x.toFloat() * time.toFloat()))
                    }

                }
            }

        }
        content()
    }
}

private fun DrawScope.drawStar(offset: Offset) {
    drawCircle(color = Color.Blue, radius = 20f, center = offset)
}

@Preview(showSystemUi = true)
@Composable
fun BackgroundView() {
    BackgroundDayAndNightView()
}