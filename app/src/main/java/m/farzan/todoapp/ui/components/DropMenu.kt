package m.farzan.todoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun DropMenu(
    expanded: MutableState<Boolean>,
    items: List<String>,
    onItemSelect: (item: String) -> Unit
) {


    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = Modifier.wrapContentSize()
    ) {

        items.forEach {

            DropdownMenuItem(onClick = { expanded.value = false; onItemSelect(it) }) {
                Text(text = it)
            }

        }

    }
}