package m.farzan.todoapp.util

import androidx.room.TypeConverter
import m.farzan.todoapp.model.Status

class StatusConverter {

    @TypeConverter
    fun stringFromStatus(status: Status): String {
        return status.name
    }

    @TypeConverter
    fun statusFromString(statusAsString: String): Status {
        return Status.valueOf(statusAsString)
    }
}