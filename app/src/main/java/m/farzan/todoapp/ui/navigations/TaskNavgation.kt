package m.farzan.todoapp.ui.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.ui.tasks.AddTaskScreen
import m.farzan.todoapp.ui.tasks.TaskViewModel
import m.farzan.todoapp.ui.tasks.TasksScreen
import m.farzan.todoapp.util.DateConverter.Companion.dateFormatter

@Composable
fun TaskNavigation() {
    val navController = rememberNavController()
    val taskViewModel = viewModel<TaskViewModel>()

    NavHost(navController = navController, startDestination = Screens.TasksScreen.name) {
        composable(route = Screens.TasksScreen.name) {
            TasksScreen(navController, taskViewModel)
        }

        composable(
            route = Screens.AddTaskScreen.name + "/{date}", arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.get("date") as String
            AddTaskScreen(navController, taskViewModel, date)
        }

        composable(
            route = Screens.ChangeTaskScreen.name + "/{taskId}", arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.get("taskId") as Int
            val task = taskViewModel.getById(id)
            AddTaskScreen(
                navController, taskViewModel, task.date.format(dateFormatter),
                task
            )
        }


    }
}