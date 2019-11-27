package com.biachacon.todolist.dialogs

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException

class ConfirmExitWOSave: DialogFragment() {

    internal lateinit var listener: ConfirmeExitWOSaveListener

    interface ConfirmeExitWOSaveListener{
        fun onQuitAnyway(dialog: DialogFragment)
        fun onCancelQuit(dialog: DialogFragment)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as ConfirmeExitWOSaveListener
        }catch (e: ClassCastException){
            throw ClassCastException((context.toString() +
                    "must be implement ConfirmeExitWOSaveListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
        val builder= AlertDialog.Builder(it)
            builder.setMessage("Sair sem salvar?")
                .setPositiveButton(
                    R.string.yes,
                    DialogInterface.OnClickListener{
                            dialog,id ->
                        listener.onQuitAnyway(this)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onCancelQuit(this)
                    })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}