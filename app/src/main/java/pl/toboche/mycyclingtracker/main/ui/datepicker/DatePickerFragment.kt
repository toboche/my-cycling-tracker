package pl.toboche.mycyclingtracker.main.ui.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.main.ui.home.AddNewRecordViewModel
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var addNewRecordViewModel: AddNewRecordViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        addNewRecordViewModel =
            ViewModelProvider(requireActivity()).get(AddNewRecordViewModel::class.java)
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