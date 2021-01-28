package pl.toboche.mycyclingtracker.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.toboche.mycyclingtracker.data.source.DefaultTrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.LocalTrackRecordDataSource
import pl.toboche.mycyclingtracker.data.source.TrackRecordDataSource
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindDefaultTrackRecordRepository(
        defaultTrackRecordDataSource: DefaultTrackRecordRepository
    ): TrackRecordRepository

    @Binds
    abstract fun bindTrackRecordDataSource(
        localTrackRecordDataSource: LocalTrackRecordDataSource
    ): TrackRecordDataSource
}