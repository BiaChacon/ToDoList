package com.biachacon.todolist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.biachacon.todolist.R
import java.lang.IllegalStateException

class EditListDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{

            var builder = AlertDialog.Builder(it)
                .setView(it.layoutInflater.inflate(R.layout.edit_list_layout ,null))
                .setPositiveButton("Save"){dialogInterface, i ->
                    Toast.makeText(it.baseContext, "SAVE!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Canceled"){dialogInterface, i ->
                    Toast.makeText(it.baseContext, "CANCELED!", Toast.LENGTH_SHORT).show()
                }
            builder.create()
        }?: throw IllegalStateException("Activity not found")
    }

}
