package com.biachacon.todolist.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.biachacon.todolist.R
import java.lang.IllegalStateException

class AddListDialogFragment:DialogFragment() {

        internal lateinit var listener: NoticeDialogListener

        interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

        override fun onAttach(context: Context) {
       super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
           listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                   " must implement NoticeDialogListener"))
       }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{

            var builder = AlertDialog.Builder(it)
                .setView(it.layoutInflater.inflate(R.layout.add_list_layout, null))
                .setPositiveButton(R.string.save){dialogInterface, i ->
                    //Toast.makeText(it.baseContext, R.string.saved, Toast.LENGTH_SHORT).show()
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(R.string.cancel){dialogInterface, i ->
                //    Toast.makeText(it.baseContext, R.string.canceled, Toast.LENGTH_SHORT).show()
                    listener.onDialogNegativeClick(this)
                }
            builder.create()
        }?: throw IllegalStateException("Activity not found")
    }
}