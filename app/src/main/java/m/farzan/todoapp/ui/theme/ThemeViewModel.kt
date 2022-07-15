package m.farzan.todoapp.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {

    companion object {

        val isDark = mutableStateOf(false)
        var isAuto = true

        fun onThemeChanged() {
            isAuto = false
            isDark.value = !isDark.value
        }
    }
}