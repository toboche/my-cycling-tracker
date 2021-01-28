package pl.toboche.mycyclingtracker.data.source

import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import javax.inject.Inject

class DefaultTrackRecordRepository @Inject constructor(
    private val tasksLocalDataSource: TrackRecordDataSource
) : TrackRecordRepository {
    override suspend fun saveTrackRecord(trackRecord: TrackRecord) {
        return tasksLocalDataSource.saveTrackRecord(trackRecord)
    }

    override suspend fun getTrackRecords(forceUpdate: Boolean): Result<List<TrackRecord>> {
        // this is a place where two repositories can be merged together
        // we haven't agreed on using any backend services
        // but any type of synchronisation could happen here
        // executed on another, remote repositories
        return tasksLocalDataSource.getTrackRecords()
    }
}