package m.farzan.todoapp.ui.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import m.farzan.todoapp.data.ExampleTasks
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.repository.TasksRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList = _taskList.asStateFlow()

    fun changeDate(localDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks(localDate).distinctUntilChanged()
                .collect { listOfTasks ->
                    _taskList.value = listOfTasks
                }
        }
    }

    fun add(task: Task) {
        viewModelScope.launch { repository.addTask(task) }
    }

    fun update(task: Task) {
        Log.d("TaskViewModel", "update: $task")
        viewModelScope.launch { repository.updateTask(task) }
    }

    fun remove(task: Task) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun getById(id: Int): Task {
        val task: Task?
        runBlocking {
            task = repository.getTaskById(id)
        }
        return task!!
    }


}
