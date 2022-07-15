package m.farzan.todoapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

const val INDEX_PRIMARY = 0
const val INDEX_SECONDARY = 1
const val INDEX_TERTIARY = 2
const val INDEX_QUANTITY = 3

@get:Composable
val primaryGradient: List<Color>
    get() = listOf(Blue2, Blue1)

@get:Composable
val secondaryGradient: List<Color>
    get() = listOf(Purple2, Purple1)

@get:Composable
val tertiaryGradient: List<Color>
    get() = listOf(Yellow2, Yellow1)

@get:Composable
val quantityGradient: List<Color>
    get() = listOf(Red2, Red1)


@Composable
fun getGradColors(gradientIndex: Int): List<Color> {
    val gradients = listOf(primaryGradient, secondaryGradient, tertiaryGradient, quantityGradient)

    return gradients[gradientIndex]

}

@Composable
fun gradient(
    gradientIndex: Int,
    startOffset: Offset = Offset(
        Float.POSITIVE_INFINITY,
        Float.POSITIVE_INFINITY
    ), endOffset: Offset = Offset(0f, 0f)
): Brush {

    return Brush.linearGradient(
        colors = getGradColors(gradientIndex),
        start = startOffset,
        end = endOffset
    )
}

@Composable
fun backgroundGradient(): Brush {
    val colors = listOf(MaterialTheme.colors.surface , MaterialTheme.colors.surface)
    return Brush.horizontalGradient(
        0f to colors[0],
        0.15f to colors[1]
    )
}