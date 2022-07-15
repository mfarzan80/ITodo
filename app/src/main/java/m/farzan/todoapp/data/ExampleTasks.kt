package m.farzan.todoapp.data

import android.util.Log
import m.farzan.todoapp.model.Status
import m.farzan.todoapp.model.Task
import java.time.LocalDate
import java.time.LocalTime


class ExampleTasks {
    companion object {
        var testTasks: List<Task>

        init {
            val today = LocalDate.now()
            testTasks =
                listOf(
                    Task(
                        "Develop Todo App1",
                        today,
                        LocalTime.of(1, 30),
                        LocalTime.of(2, 10),
                        Status.Todo
                    ),
                    Task(
                        "Develop Todo App2",
                        today,
                        LocalTime.of(12, 30),
                        LocalTime.of(13, 5),
                        Status.Done
                    ),
                    Task(
                        "Develop Todo App3",
                        today,
                        LocalTime.of(7, 30),
                        LocalTime.of(10, 20),
                        Status.Doing
                    ),
                    Task(
                        "Develop Todo App3",
                        today,
                        LocalTime.of(15, 30),
                        LocalTime.of(18, 45),
                        Status.Undone
                    ),

                    )
            Log.d("TasksScreen", "Tasks init " + testTasks[0])
        }

    }
}
