package pl.toboche.mycyclingtracker.service.date

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarService @Inject constructor() : CalendarApi {
    override fun getNow(): Calendar = Calendar.getInstance()
}