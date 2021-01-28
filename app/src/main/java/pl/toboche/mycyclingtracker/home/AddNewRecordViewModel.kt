package pl.toboche.mycyclingtracker.home

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import pl.toboche.mycyclingtracker.service.date.CalendarApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddNewRecordViewModel @Inject constructor(
    private val calendarApi: CalendarApi,
    private val trackRecordRepository: TrackRecordRepository
) : ViewModel() {

    val name = MutableLiveData<String>().apply {
        value = ""
    }
    val distance = MutableLiveData<String>().apply {
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

    val errorText = MutableLiveData<String?>()

    fun setDate(year: Int, month: Int, day: Int) {
        val newDate = calendarApi.getNow().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        _date.postValue(newDate)
    }

    fun save() {
        if (name.value.isNullOrEmpty()) {
            errorText.value = "please provide a title"
            return
        }
        if (distance.value.isNullOrEmpty()) {
            errorText.value = "please provide distance"
            return
        }
        val distanceValue = distance.value!!
        if (distanceValue.toDoubleOrNull() == null) {
            //TODO show error bad input
            return
        }
        viewModelScope.launch {
            trackRecordRepository.saveTrackRecord(
                TrackRecord(
                    title = name.value.orEmpty(),
                    comments = comments.value,
                    date = Date.from(_date.value!!.toInstant()),
                    distance = distanceValue.toDouble()
                )
            )
            name.value = ""
            comments.value = ""
            distance.value = ""
        }
    }
}