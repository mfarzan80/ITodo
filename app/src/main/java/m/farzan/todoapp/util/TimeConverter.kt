package m.farzan.todoapp.util

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeConverter {

    companion object{
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    }

    @TypeConverter
    fun intFromTime(localTime: LocalTime): Int {
        return localTime.hour * 60 + localTime.minute
    }

    @TypeConverter
    fun dateFromInt(timeAsInt: Int): LocalTime {
        val hour = timeAsInt / 60
        return LocalTime.of(hour , timeAsInt - hour * 60 )
    }
}