package pl.toboche.mycyclingtracker.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TrackRecord(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "comments") val comments: String?,
    @ColumnInfo(name = "date") val date: Date
)