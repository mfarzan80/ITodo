package m.farzan.todoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Spinner(
    modifier: Modifier = Modifier, items: Array<String>, defaultIndex: Int = 0,
    onItemChange: (index: Int, item: String) -> Unit
) {

    var specimenText = items[defaultIndex]
    var expanded by remember { mutableStateOf(false) }


    Box(modifier = modifier.clickable(
        onClick = { expanded = !expanded }
    )) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = specimenText,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.wrapContentSize()
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            specimenText = item
                            onItemChange(items.indexOf(item) , item)
                        }) {
                            Text(text = item)
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.width(3.dp))
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "drop down icon",
                tint = MaterialTheme.colors.onSecondary
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}
