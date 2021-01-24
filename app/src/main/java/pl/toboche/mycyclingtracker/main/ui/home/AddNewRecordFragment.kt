package pl.toboche.mycyclingtracker.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.R
import pl.toboche.mycyclingtracker.main.ui.datepicker.DatePickerFragment
import pl.toboche.mycyclingtracker.main.ui.service.date.CalendarService

class AddNewRecordFragment : Fragment() {

    private lateinit var addNewRecordViewModel: AddNewRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNewRecordViewModel =
            ViewModelProvider(requireActivity(), AddNewRecordViewModelFactory(CalendarService()))
                .get(AddNewRecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_new_record, container, false)
        val dateText: TextView = root.findViewById(R.id.add_new_record_date_description)
        val changeDateButton: Button = root.findViewById(R.id.add_new_record_change_date_button)
        val saveButton: Button = root.findViewById(R.id.add_new_record_save_button)
        addNewRecordViewModel.dateText.observe(viewLifecycleOwner) {
            dateText.text = it
        }
        changeDateButton.setOnClickListener {
            DatePickerFragment()
                .show(childFragmentManager, "datePicker")
        }
        saveButton.setOnClickListener {
            addNewRecordViewModel.save()
        }
        return root
    }
}