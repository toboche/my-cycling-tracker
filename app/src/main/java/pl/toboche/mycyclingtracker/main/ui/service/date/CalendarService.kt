package pl.toboche.mycyclingtracker.main.ui.service.date

import java.util.*

class CalendarService : CalendarApi {
    override fun getNow(): Calendar = Calendar.getInstance()
}