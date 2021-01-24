package pl.toboche.mycyclingtracker.main.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.main.ui.service.date.CalendarApi

class AddNewRecordViewModelFactory(
    private val calendarApi: CalendarApi
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewRecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddNewRecordViewModel(calendarApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}