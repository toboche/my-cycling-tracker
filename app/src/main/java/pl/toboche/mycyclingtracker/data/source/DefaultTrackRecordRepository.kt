package pl.toboche.mycyclingtracker.data.source

import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord

class DefaultTrackRecordRepository(
    private val tasksLocalDataSource: TrackRecordDataSource
) : TrackRecordRepository {
    override suspend fun saveTrackRecord(trackRecord: TrackRecord) {
        return tasksLocalDataSource.saveTrackRecord(trackRecord)
    }

    override suspend fun getTrackRecords(forceUpdate: Boolean): Result<List<TrackRecord>> {
        return tasksLocalDataSource.getTrackRecords()
    }
}