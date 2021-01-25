package pl.toboche.mycyclingtracker.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrackRecordDao {
    @Query("SELECT * FROM trackrecord")
    fun getAll(): List<TrackRecord>

    @Insert
    fun insert(trackRecord: TrackRecord)
}