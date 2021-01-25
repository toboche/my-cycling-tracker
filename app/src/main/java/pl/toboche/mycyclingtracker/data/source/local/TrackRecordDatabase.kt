package pl.toboche.mycyclingtracker.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TrackRecord::class], version = 1)
@TypeConverters(Converters::class)
abstract class TrackRecordDatabase : RoomDatabase() {
    abstract fun userDao(): TrackRecordDao
}