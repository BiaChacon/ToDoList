package com.biachacon.todolist.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R
import com.biachacon.todolist.dialogs.ConfirmDeleteDialog
import com.biachacon.todolist.dialogs.ConfirmFinishedDialog
import com.biachacon.todolist.model.Task

class TaskAdapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>()
    ,ConfirmFinishedDialog.NoticeDialogListener,ConfirmDeleteDialog.NoticeDialogListener {

    //variável para saber de foi confimado no dialog
    var confirmed = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.task_inflater, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val taskAtual = tasks[position]

        holder.nameTask.text = taskAtual.name
        holder.dateTask.text = taskAtual.date
        //var t:ToDoList
        //t = ToDoListDao().findById(taskAtual.id_ToDoList)
        holder.nameList.text = taskAtual.id_ToDoList.toString()

        holder.finished.setOnClickListener {
            showNoticeDialog()
            if(confirmed){
                //remove da lista atual e coloca na lista de finalizadas
                notifyItemRemoved(position)
            }
        }

        holder.deleteTask.setOnClickListener {
            showNoticeDialog()
            if (confirmed){
                //deleta a task do banco
                notifyItemRemoved(position)
            }
        }



       /* if (taskAtual.finished){
            holder.finished.isChecked = true
        }*/

    }


    fun showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = ConfirmFinishedDialog()
        dialog.show(FragmentActivity().supportFragmentManager, "FinishedDialog")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        confirmed =true
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        confirmed = false
    }

    override fun onDialogPositiveClickD(dialog: DialogFragment) {
        confirmed = true
    }

    override fun onDialogNegativeClickD(dialog: DialogFragment) {
        confirmed = false
    }

}