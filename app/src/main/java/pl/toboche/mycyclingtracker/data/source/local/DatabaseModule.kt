package pl.toboche.mycyclingtracker.data.source.local

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideTrackRecordDatabase(application: Application): TrackRecordDatabase {
        return Room.databaseBuilder(
            application,
            TrackRecordDatabase::class.java, "Tasks.db"
        ).build()
    }

    @Provides
    fun provideTrackRecorderDao(trackRecordDatabase: TrackRecordDatabase): TrackRecordDao {
        return trackRecordDatabase.trackRecorderDao()
    }

}