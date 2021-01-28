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

    val error = MutableLiveData<String?>()

    fun loadTrackRecords() {
        viewModelScope.launch {
            trackRecordRepository.getTrackRecords().apply {
                when (this) {
                    is Result.Success -> {
                        val mapToItem = mapToItem(this.data)
                        trackRecords.value = mapToItem
                    }
                    is Result.Error -> showErrorReadingData()
                    is Result.Loading -> showLoading()
                }
            }
        }
    }

    private fun mapToItem(data: List<TrackRecord>): List<TrackRecordItem> {
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
        //this text should be moved to resources or, even better, to some repository
        error.value = "error loading data"
    }

    data class TrackRecordItem(
        val title: String,
        val comments: String?,
        val distance: String,
        val date: String
    )
}