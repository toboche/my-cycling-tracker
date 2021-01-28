package pl.toboche.mycyclingtracker.dashboard.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.toboche.mycyclingtracker.DashboardViewModel
import pl.toboche.mycyclingtracker.R

class TrackRecordsAdapter(
    private val trackRecords: List<DashboardViewModel.TrackRecordItem>,
    private val context: Context
) : RecyclerView.Adapter<TrackRecordsAdapter.TrackRecordViewHolder>() {

    class TrackRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.item_track_record_title)
        private var comments: TextView = itemView.findViewById(R.id.item_track_record_comments)
        private var date: TextView = itemView.findViewById(R.id.item_track_record_date)
        private var distance: TextView = itemView.findViewById(R.id.item_track_record_distance)

        fun setTrackRecord(trackRecord: DashboardViewModel.TrackRecordItem) {
            title.text = trackRecord.title
            comments.text = trackRecord.comments
            date.text = trackRecord.date
            distance.text = trackRecord.distance
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackRecordViewHolder {
        return TrackRecordViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.item_track_record,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: TrackRecordViewHolder, position: Int) {
        holder.setTrackRecord(trackRecords[position])
    }

    override fun getItemCount() = trackRecords.size
}
