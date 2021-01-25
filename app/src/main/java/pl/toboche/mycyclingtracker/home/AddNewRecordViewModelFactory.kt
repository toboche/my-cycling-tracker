package pl.toboche.mycyclingtracker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.service.date.CalendarApi

class AddNewRecordViewModelFactory(
    private val calendarApi: CalendarApi,
    private val trackRecordRepository: TrackRecordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewRecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddNewRecordViewModel(
                calendarApi,
                trackRecordRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}