package m.farzan.todoapp.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import m.farzan.todoapp.data.TasksDatabaseDao
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.util.DateConverter
import java.time.LocalDate
import javax.inject.Inject

class TasksRepository @Inject constructor(private val tasksDatabaseDao: TasksDatabaseDao) {
    suspend fun addTask(task: Task) {
        tasksDatabaseDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        tasksDatabaseDao.updateTask(task = task)
    }

    suspend fun deleteTask(task: Task) {
        tasksDatabaseDao.deleteTask(task)
    }

    fun getAllTasks(localDate: LocalDate): Flow<List<Task>> {
        return tasksDatabaseDao.getTasks(DateConverter().stringFromDate(localDate)).flowOn(Dispatchers.IO).conflate()
    }

    suspend fun getTaskById(id: Int): Task{
        return tasksDatabaseDao.getTaskById(id)
    }


}