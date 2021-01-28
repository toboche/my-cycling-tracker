package pl.toboche.mycyclingtracker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.toboche.mycyclingtracker.home.AddNewRecordViewModel
import pl.toboche.mycyclingtracker.home.AddNewRecordViewModelFactory
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var addNewRecordViewModelFactory: AddNewRecordViewModelFactory

    private val addNewRecordViewModel: AddNewRecordViewModel by activityViewModels { addNewRecordViewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = addNewRecordViewModel.date.value!!
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        addNewRecordViewModel.setDate(year, month, day)
    }
}