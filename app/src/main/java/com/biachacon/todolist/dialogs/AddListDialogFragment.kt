package com.biachacon.todolist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.biachacon.todolist.R
import java.lang.IllegalStateException

class AddListDialogFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{

            var builder = AlertDialog.Builder(it)
                .setView(it.layoutInflater.inflate(R.layout.add_list_layout, null))
                .setPositiveButton(R.string.save){dialogInterface, i ->
                    Toast.makeText(it.baseContext, R.string.saved, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.cancel){dialogInterface, i ->
                    Toast.makeText(it.baseContext, R.string.canceled, Toast.LENGTH_SHORT).show()
                }
            builder.create()
        }?: throw IllegalStateException("Activity not found")
    }

}