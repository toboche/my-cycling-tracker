package pl.toboche.mycyclingtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.toboche.mycyclingtracker.dashboard.ui.TrackRecordsAdapter
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    @Inject
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val trackRecordsRecyclerView: RecyclerView =
            root.findViewById(R.id.track_records_recycler_view)
        val emptyListText: TextView = root.findViewById(R.id.empty_list_text)
        trackRecordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        trackRecordsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        dashboardViewModel.trackRecords.observe(viewLifecycleOwner, {
            val trackRecordsAdapter = TrackRecordsAdapter(it, requireContext())
            trackRecordsRecyclerView.adapter = trackRecordsAdapter
        })
        dashboardViewModel.loadTrackRecords()
        dashboardViewModel.error.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                dashboardViewModel.error.value = null
            }
        })
        dashboardViewModel.emptyText.observe(viewLifecycleOwner, {
            emptyListText.text = it
        })
        return root
    }
}