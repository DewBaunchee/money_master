package by.varyvoda.android.moneymaster.data.model.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

typealias GradientColors = List<Color>

fun GradientColors.toBrush() =
    Brush.linearGradient(
        colors = this,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

fun Color.toBrush(): Brush = Brush.horizontalGradient(listOf(this, this))
