package pl.toboche.mycyclingtracker.data.source

import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord

interface TrackRecordRepository {
    suspend fun saveTrackRecord(trackRecord: TrackRecord)

    suspend fun getTrackRecords(forceUpdate: Boolean = false): Result<List<TrackRecord>>
}