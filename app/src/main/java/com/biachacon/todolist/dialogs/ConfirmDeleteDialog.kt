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
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Deletar Permanentemente?")
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener{
                            dialog,id ->
                        listener.onDialogPositiveClickD(this)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClickD(this)
                    })

            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}