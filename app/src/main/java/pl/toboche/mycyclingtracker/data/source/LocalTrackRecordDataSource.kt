package pl.toboche.mycyclingtracker.data.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import pl.toboche.mycyclingtracker.data.source.local.TrackRecordDao

class LocalTrackRecordDataSource(
    private val trackRecordDao: TrackRecordDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackRecordDataSource {
    override suspend fun getTrackRecords(): Result<List<TrackRecord>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(trackRecordDao.getAll())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTrackRecord(trackRecord: TrackRecord) = withContext(ioDispatcher) {
        trackRecordDao.insert(trackRecord)
    }
}