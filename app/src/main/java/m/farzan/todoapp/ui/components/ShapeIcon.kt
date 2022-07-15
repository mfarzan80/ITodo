package m.farzan.todoapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShapeIcon(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(0.dp),
    elevation: Dp = 0.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {

    Card(modifier = modifier, onClick = onClick , backgroundColor = backgroundColor , shape = shape, elevation = elevation) {
        content()
    }

}