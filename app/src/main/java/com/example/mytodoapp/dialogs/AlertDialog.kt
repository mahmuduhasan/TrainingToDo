package com.example.mytodoapp.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.mytodoapp.R

class AlertDialog(
    val title : String,
    val body : String,
    val positiveText : String,
    val negativeText : String,
    val icon : Int = R.drawable.ic_baseline_delete_24,
    val positiveClickCallback : () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setIcon(icon)
        builder.setPositiveButton(positiveText) { dialog, which ->
            positiveClickCallback()
        }
        builder.setNegativeButton(negativeText, null)
        return builder.create()
        }
    }
