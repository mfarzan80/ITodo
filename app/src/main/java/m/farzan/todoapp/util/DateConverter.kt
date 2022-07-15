package m.farzan.todoapp.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverter {
    companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    }

    @TypeConverter
    fun stringFromDate(localDate: LocalDate): String {
        return dateFormatter.format(localDate)
    }

    @TypeConverter
    fun dateFromString(dateAsString: String): LocalDate {
        return LocalDate.from(dateFormatter.parse(dateAsString))
    }
}