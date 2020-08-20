package com.mrspd.deltaonsiteround4

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class DialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val min = c[Calendar.MINUTE]
        return TimePickerDialog(
            activity,
            activity as OnTimeSetListener?,
            hour,
            min,
            DateFormat.is24HourFormat(activity)
        )
    }
}