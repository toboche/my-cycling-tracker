package pl.toboche.mycyclingtracker.data.source

import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord

interface TrackRecordDataSource {
    suspend fun getTrackRecords(): Result<List<TrackRecord>>

    suspend fun saveTrackRecord(trackRecord: TrackRecord)
}