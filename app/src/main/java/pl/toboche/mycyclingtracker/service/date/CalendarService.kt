package pl.toboche.mycyclingtracker.service.date

import java.util.*

class CalendarService : CalendarApi {
    override fun getNow(): Calendar = Calendar.getInstance()
}