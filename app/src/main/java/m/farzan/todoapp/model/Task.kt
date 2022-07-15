package m.farzan.todoapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity(tableName = "tbl_tasks")
data class Task(
    var tittle: String,
    var date: LocalDate,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var status: Status,

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


    fun edit(
        tittle: String,
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
    ) {
        this.tittle = tittle
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
    }

}
