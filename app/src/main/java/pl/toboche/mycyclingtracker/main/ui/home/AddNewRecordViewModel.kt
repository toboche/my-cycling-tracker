package pl.toboche.mycyclingtracker.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import pl.toboche.mycyclingtracker.main.ui.service.date.CalendarApi
import java.text.SimpleDateFormat
import java.util.*

class AddNewRecordViewModel(
    private val calendarApi: CalendarApi
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val name = MutableLiveData<String>().apply {
        value = ""
    }
    private val _date = MutableLiveData<Calendar>().apply {
        value = calendarApi.getNow()
    }
    val dateText = _date.map {
        SimpleDateFormat.getDateInstance()
            .format(it.time)
    }
    val date: LiveData<Calendar> = _date
    val text: LiveData<String> = _text

    fun setDate(year: Int, month: Int, day: Int) {
        val newDate = calendarApi.getNow().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        _date.postValue(newDate)
    }
}