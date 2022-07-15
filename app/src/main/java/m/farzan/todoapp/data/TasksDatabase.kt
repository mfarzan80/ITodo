package m.farzan.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.util.DateConverter
import m.farzan.todoapp.util.StatusConverter
import m.farzan.todoapp.util.TimeConverter

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, TimeConverter::class, StatusConverter::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDatabaseDao
}