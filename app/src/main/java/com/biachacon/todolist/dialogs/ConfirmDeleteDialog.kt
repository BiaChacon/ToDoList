package com.biachacon.todolist.dialogs


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.R
import android.content.Context
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.Toast
class ConfirmDeleteDialog: DialogFragment() {

    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClickD(dialog: DialogFragment)
        fun onDialogNegativeClickD(dialog: DialogFragment)
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
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Deletar Permanentemente?")
                .setPositiveButton("SIM",
                    DialogInterface.OnClickListener{
                            dialog,id ->
                        listener.onDialogPositiveClickD(this)
                    })
                .setNegativeButton("NÃO",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClickD(this)
                    })

            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}