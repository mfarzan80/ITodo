package m.farzan.todoapp.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple2,
    primaryVariant = Purple2,
    secondary = Blue2,
    background = Dark1,
    surface = Dark2,
    onPrimary = White,
    onSecondary = White,
    onBackground = Gray10,
    onSurface = Gray10,
)

private val LightColorPalette = lightColors(
    primary = Purple2,
    primaryVariant = Purple2,
    secondary = Blue2,
    background = White,
    surface = Gray10,
    onPrimary = White,
    onSecondary = White,
    onBackground = Dark2,
    onSurface = Dark2,
)

private val animationSpec: AnimationSpec<Color> = tween(durationMillis = 500)

@Composable
private fun animateColor(
    targetValue: Color
) = animateColorAsState(targetValue = targetValue, animationSpec = animationSpec).value


@Composable
fun Colors.switch(): Colors {
    return copy(
        primary = animateColor(primary),
        primaryVariant = animateColor(primaryVariant),
        secondary = animateColor(secondary),
        background = animateColor(background),
        surface = animateColor(surface),
        onPrimary = animateColor(onPrimary),
        onSecondary = animateColor(onSecondary),
        onBackground = animateColor(onBackground),
        onSurface = animateColor(onSurface)
    )
}


@get:Composable
val Colors.HintColor: Color
    get() = onBackground.copy(.5f)

@get:Composable
val HintColorAlpha: Color
    get() = White.copy(.8f)


@Composable
fun TodoAppTheme(content: @Composable () -> Unit) {

    val animatedColors = (if (!ThemeViewModel.isDark.value)
        LightColorPalette else DarkColorPalette).switch()

    MaterialTheme(
        colors = animatedColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}