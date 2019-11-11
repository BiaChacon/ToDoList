package com.biachacon.todolist.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.R
import android.widget.Toast

class ConfirmFinishedDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Finalizada?")
                .setPositiveButton("SIM",
                    DialogInterface.OnClickListener{
                        dialog,id ->
                        Toast.makeText(it.baseContext, "Mensagem enviada!", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("NÃO",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(it.baseContext, "Cancelado", Toast.LENGTH_SHORT).show()
                    })

            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}