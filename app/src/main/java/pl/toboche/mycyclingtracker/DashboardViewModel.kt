package pl.toboche.mycyclingtracker

import androidx.lifecycle.LiveData
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

    val _emptyText = MutableLiveData<String?>()
    val emptyText: LiveData<String?> = _emptyText

    fun loadTrackRecords() {
        viewModelScope.launch {
            trackRecordRepository.getTrackRecords().apply {
                when (this) {
                    is Result.Success -> {
                        val mappedItems = mapToItem(this.data)
                        if (mappedItems.isEmpty()) {
                            _emptyText.value = "no items to show"
                        } else {
                            _emptyText.value = null
                        }
                        trackRecords.value = mappedItems
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
        //not needed for now, skipping.
        // Normally I'd remove this code entirely,
        // but want to show how things would work id there
        // was some backend comms required
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