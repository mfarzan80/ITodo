package m.farzan.todoapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import m.farzan.todoapp.data.TasksDatabase
import m.farzan.todoapp.data.TasksDatabaseDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideTasksDao(tasksDatabase: TasksDatabase): TasksDatabaseDao = tasksDatabase.tasksDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(context, TasksDatabase::class.java, "tasks_db")
            .fallbackToDestructiveMigration().build()
    }

}