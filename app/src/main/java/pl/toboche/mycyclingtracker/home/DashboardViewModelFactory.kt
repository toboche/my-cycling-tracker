package pl.toboche.mycyclingtracker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.DashboardViewModel
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository

class DashboardViewModelFactory(
    private val trackRecordRepository: TrackRecordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(
                trackRecordRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}