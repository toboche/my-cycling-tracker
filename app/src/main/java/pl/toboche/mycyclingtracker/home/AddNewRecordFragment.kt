package pl.toboche.mycyclingtracker.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import pl.toboche.mycyclingtracker.DatePickerFragment
import pl.toboche.mycyclingtracker.R
import pl.toboche.mycyclingtracker.data.source.DefaultTrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.LocalTrackRecordDataSource
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.TrackRecordDatabase
import pl.toboche.mycyclingtracker.service.date.CalendarService

class AddNewRecordFragment : Fragment() {

    private lateinit var addNewRecordViewModel: AddNewRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNewRecordViewModel =
            ViewModelProvider(
                requireActivity(), AddNewRecordViewModelFactory(
                    CalendarService(),
                    createTrackRecordRepository()
                )
            )
                .get(AddNewRecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_new_record, container, false)
        val dateText: TextView = root.findViewById(R.id.add_new_record_date_description)
        val changeDateButton: Button = root.findViewById(R.id.add_new_record_change_date_button)
        val saveButton: Button = root.findViewById(R.id.add_new_record_save_button)
        val nameEditText: EditText = root.findViewById(R.id.add_new_record_name)
        val commentsEditText: EditText = root.findViewById(R.id.add_new_record_comments)

        addNewRecordViewModel.dateText.observe(viewLifecycleOwner) { dateText.text = it }
        addNewRecordViewModel.name.observe(viewLifecycleOwner, {
            if (it != nameEditText.text.toString()) {
                nameEditText.setText(it)
            }
        })
        addNewRecordViewModel.comments.observe(viewLifecycleOwner, {
            if (it != commentsEditText.text.toString()) {
                commentsEditText.setText(it)
            }
        })

        changeDateButton.setOnClickListener {
            DatePickerFragment()
                .show(childFragmentManager, "datePicker")
        }
        saveButton.setOnClickListener {
            addNewRecordViewModel.save()
        }
        nameEditText.addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }
            addNewRecordViewModel.name.value = it.toString()
        }
        commentsEditText.addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }
            addNewRecordViewModel.comments.value = it.toString()
        }
        return root
    }

    private fun createTrackRecordRepository(): TrackRecordRepository {
        return DefaultTrackRecordRepository(
            LocalTrackRecordDataSource(
                createDataBase(requireContext()).userDao()
            )
        )
    }

    private fun createDataBase(context: Context): TrackRecordDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            TrackRecordDatabase::class.java, "Tasks.db"
        ).build()
//        database = result
        return result
    }
}