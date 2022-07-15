package m.farzan.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import m.farzan.todoapp.ui.navigations.TaskNavigation
import m.farzan.todoapp.ui.theme.ThemeViewModel
import m.farzan.todoapp.ui.theme.TodoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (ThemeViewModel.isAuto)
                ThemeViewModel.isDark.value = isSystemInDarkTheme()
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr ) {
                TodoAppTheme {
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setSystemBarsColor(MaterialTheme.colors.surface)
                    TaskNavigation()
                }
            }
        }
    }
}

