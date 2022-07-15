package m.farzan.todoapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import m.farzan.todoapp.ui.theme.HintColor
import m.farzan.todoapp.ui.theme.HintType

@Composable
fun HintText(modifier: Modifier = Modifier , text:String ){
    Text(
        modifier = modifier,
        text = text, style = MaterialTheme.typography.HintType.copy(fontSize = 15.sp),
        color = MaterialTheme.colors.HintColor,
    )
}

@Composable
fun HintTextBigger(modifier: Modifier = Modifier , text:String ){
    Text(
        modifier = modifier,
        text = text, style = MaterialTheme.typography.HintType.copy(fontSize = 17.sp),
        color = MaterialTheme.colors.HintColor,
    )
}