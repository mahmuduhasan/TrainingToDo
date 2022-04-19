package com.example.mytodoapp.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class TimePicker(val timeCallBack : (String) -> Unit) : DialogFragment() , TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = Calendar.getInstance()
        val hour = time.get(Calendar.HOUR)
        val minute = time.get(Calendar.MINUTE)

        return TimePickerDialog(requireActivity(), this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val time = Calendar.getInstance()
        time.set(0,0,0,p1, p2)
        val selectedTime = SimpleDateFormat("hh:mm a").format(Date(time.timeInMillis))
        timeCallBack(selectedTime)
    }
}