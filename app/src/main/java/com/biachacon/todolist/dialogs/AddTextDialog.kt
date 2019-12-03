package com.biachacon.todolist.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.biachacon.todolist.R
import java.lang.IllegalStateException

class AddTextDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let{
            val builder = AlertDialog.Builder(it)
                builder.setMessage(getString(R.string.addNameToSave))
                    .setPositiveButton(
                       R.string.ok,
                        DialogInterface.OnClickListener{
                            dialog, id ->
                        })
            builder.create()
        }?: throw IllegalStateException("activity cannot be null")
    }
}