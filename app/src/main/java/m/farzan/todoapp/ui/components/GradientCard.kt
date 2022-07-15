package m.farzan.todoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import m.farzan.todoapp.ui.theme.Yellow1
import m.farzan.todoapp.ui.theme.Yellow2

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    gradient: Brush,
    elevation: Dp = 0.dp,
    elevationColor: Color,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape, spotColor = elevationColor),
        onClick = onClick,
        elevation = 0.dp,
        shape = shape,
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(brush = gradient)
        ) {
            content()
        }
    }
}