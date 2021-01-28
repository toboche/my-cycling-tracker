package pl.toboche.mycyclingtracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import java.text.SimpleDateFormat
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val trackRecordRepository: TrackRecordRepository
) : ViewModel() {

    val trackRecords: MutableLiveData<List<TrackRecordItem>> =
        MutableLiveData<List<TrackRecordItem>>()

    fun loadTrackRecords() {
        println("asda1")
        viewModelScope.launch {
            trackRecordRepository.getTrackRecords().apply {
                when (this) {
                    is Result.Success -> {
                        println("success")
                        val mapToItem = mapToItem(this.data)
                        println(mapToItem)
                        trackRecords.value = mapToItem
                    }
                    is Result.Error -> showErrorReadingData()
                    is Result.Loading -> showLoading()
                }
            }
        }
    }

    private fun mapToItem(data: List<TrackRecord>): List<TrackRecordItem> {
        println(data)
        println("aaaaaaa")
        return data.map {
            TrackRecordItem(
                title = it.title,
                comments = it.comments,
                distance = it.distance.toString(),
                date = SimpleDateFormat.getDateInstance().format(it.date)
            )
        }
    }


    private fun showLoading() {
        //TODO
    }

    private fun showErrorReadingData() {
        //TODO
    }

    data class TrackRecordItem(
        val title: String,
        val comments: String?,
        val distance: String,
        val date: String
    )
}