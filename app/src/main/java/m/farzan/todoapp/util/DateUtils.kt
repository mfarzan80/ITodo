package m.farzan.todoapp.util

import java.time.LocalDate

class DateUtils {
    companion object {
        fun equalsDate(date1: LocalDate, date2: LocalDate): Boolean {
            if (date1.year == date2.year)
                if (date1.monthValue == date2.monthValue)
                    if (date1.dayOfMonth == date2.dayOfMonth)
                        return true

            return false
        }

        fun datesBetween(start: LocalDate, end: LocalDate): List<LocalDate> {
            val totalDates = ArrayList<LocalDate>()
            var startCopy = start
            while (!startCopy.isAfter(end)) {
                totalDates.add(startCopy)
                startCopy = startCopy.plusDays(1)
            }
            return totalDates
        }
    }
}