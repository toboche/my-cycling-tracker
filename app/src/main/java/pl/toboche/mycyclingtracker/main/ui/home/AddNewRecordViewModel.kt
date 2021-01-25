package pl.toboche.mycyclingtracker.main.ui.home

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import pl.toboche.mycyclingtracker.main.ui.service.date.CalendarApi
import java.text.SimpleDateFormat
import java.util.*

class AddNewRecordViewModel(
    private val calendarApi: CalendarApi,
    private val trackRecordRepository: TrackRecordRepository
) : ViewModel() {

    val name = MutableLiveData<String>().apply {
        value = ""
    }

    val comments = MutableLiveData<String>().apply {
        value = ""
    }

    private val _date = MutableLiveData<Calendar>().apply {
        value = calendarApi.getNow()
    }
    val date: LiveData<Calendar> = _date
    val dateText = _date.map {
        SimpleDateFormat.getDateInstance()
            .format(it.time)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val newDate = calendarApi.getNow().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        _date.postValue(newDate)
    }

    //TODO
    fun save() {
        if (name.value.isNullOrEmpty()) {
            //TODO: show error missing name
            return
        }
        viewModelScope.launch {
            trackRecordRepository.saveTrackRecord(
                TrackRecord(
                    title = name.value.orEmpty(),
                    comments = comments.value,
                    date = Date.from(_date.value!!.toInstant())
                )
            )
            Log.d("asdasd", "save: " + trackRecordRepository.getTrackRecords())
        }
    }
}