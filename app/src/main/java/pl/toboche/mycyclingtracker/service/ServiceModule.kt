package pl.toboche.mycyclingtracker.service

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.toboche.mycyclingtracker.service.date.CalendarApi
import pl.toboche.mycyclingtracker.service.date.CalendarService

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindCalendarApi(
        calendarService: CalendarService
    ): CalendarApi
}