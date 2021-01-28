package pl.toboche.mycyclingtracker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.toboche.mycyclingtracker.DatePickerFragment
import pl.toboche.mycyclingtracker.R
import javax.inject.Inject

@AndroidEntryPoint
class AddNewRecordFragment : Fragment() {

    @Inject
    lateinit var addNewRecordViewModelFactory: AddNewRecordViewModelFactory

    private val addNewRecordViewModel: AddNewRecordViewModel by activityViewModels { addNewRecordViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_new_record, container, false)
        val dateText: TextView = root.findViewById(R.id.add_new_record_date_description)
        val changeDateButton: Button = root.findViewById(R.id.add_new_record_change_date_button)
        val saveButton: Button = root.findViewById(R.id.add_new_record_save_button)
        val titleEditText: EditText = root.findViewById(R.id.add_new_record_title)
        val distanceEditText: EditText = root.findViewById(R.id.add_new_record_distance)
        val commentsEditText: EditText = root.findViewById(R.id.add_new_record_comments)

        observeDateText(dateText)
        observeTitleText(titleEditText)
        observeDistanceText(distanceEditText)
        observeCommentsText(commentsEditText)
        observeErrorText()

        openDatePickerWhenChangeDateButtonClicked(changeDateButton)
        saveEntryWhenSaveButtonClicked(saveButton)
        updateViewModelWhenTitleTextChanged(titleEditText)
        updateViewModelWhenDistanceTextChanged(distanceEditText)
        updateViewModelWhenCommentsChanged(commentsEditText)
        return root
    }

    private fun observeErrorText() {
        addNewRecordViewModel.errorText.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                addNewRecordViewModel.errorText.value = null
            }
        })
    }

    private fun updateViewModelWhenCommentsChanged(commentsEditText: EditText) {
        commentsEditText.addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }
            addNewRecordViewModel.comments.value = it.toString()
        }
    }

    private fun updateViewModelWhenDistanceTextChanged(distanceEditText: EditText) {
        distanceEditText.addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }
            addNewRecordViewModel.distance.value = it.toString()
        }
    }

    private fun updateViewModelWhenTitleTextChanged(titleEditText: EditText) {
        titleEditText.addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }
            addNewRecordViewModel.title.value = it.toString()
        }
    }

    private fun saveEntryWhenSaveButtonClicked(saveButton: Button) {
        saveButton.setOnClickListener {
            addNewRecordViewModel.save()
        }
    }

    private fun openDatePickerWhenChangeDateButtonClicked(changeDateButton: Button) {
        changeDateButton.setOnClickListener {
            DatePickerFragment()
                .show(childFragmentManager, "datePicker")
        }
    }

    private fun observeCommentsText(commentsEditText: EditText) {
        addNewRecordViewModel.comments.observe(viewLifecycleOwner, {
            if (it != commentsEditText.text.toString()) {
                commentsEditText.setText(it)
            }
        })
    }

    private fun observeDistanceText(distanceEditText: EditText) {
        addNewRecordViewModel.distance.observe(viewLifecycleOwner, {
            if (it != distanceEditText.text.toString()) {
                distanceEditText.setText(it)
            }
        })
    }

    private fun observeTitleText(titleEditText: EditText) {
        addNewRecordViewModel.title.observe(viewLifecycleOwner, {
            if (it != titleEditText.text.toString()) {
                titleEditText.setText(it)
            }
        })
    }

    private fun observeDateText(dateText: TextView) {
        addNewRecordViewModel.dateText.observe(viewLifecycleOwner) { dateText.text = it }
    }
}