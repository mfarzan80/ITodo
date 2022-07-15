package m.farzan.todoapp.data

import androidx.compose.runtime.MutableState
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.farzan.todoapp.model.Task


@Dao
interface TasksDatabaseDao {

    @Query("SELECT * FROM tbl_tasks WHERE date=:date ORDER BY startTime")
    fun getTasks(date: String): Flow<List<Task>>

    @Query("SELECT * FROM tbl_tasks WHERE id =:id")
    suspend fun getTaskById(id: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
