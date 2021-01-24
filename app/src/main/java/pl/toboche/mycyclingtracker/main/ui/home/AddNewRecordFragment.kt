package pl.toboche.mycyclingtracker.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.toboche.mycyclingtracker.R

class AddNewRecordFragment : Fragment() {

    private lateinit var addNewRecordViewModel: AddNewRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNewRecordViewModel =
            ViewModelProvider(this).get(AddNewRecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_new_record, container, false)
        val textView: TextView = root.findViewById(R.id.text_add_new_record)
        addNewRecordViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}