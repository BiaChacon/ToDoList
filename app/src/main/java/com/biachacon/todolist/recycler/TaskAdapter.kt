package com.biachacon.todolist.recycler

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R
import com.biachacon.todolist.dialogs.ConfirmDeleteDialog
import com.biachacon.todolist.dialogs.ConfirmFinishedDialog
import com.biachacon.todolist.model.Task
import java.util.*

class TaskAdapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>()
    ,ConfirmFinishedDialog.NoticeDialogListener,ConfirmDeleteDialog.NoticeDialogListener {



    //private val PENDING_REMOVAL_TIMEOUT:Long = 3000
    //var itemsPendingRemoval = ArrayList<Task>()

    //private val handler = Handler()
    //var pendingRunnables: HashMap<Task, Runnable> = HashMap()

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
        holder.dateTask.text = taskAtual.date.toString()
        holder.nameList.text = taskAtual.name


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


        /*
        if (itemsPendingRemoval.contains(taskAtual)) {

            holder.layoutNormal.setVisibility(View.GONE)
            holder.layoutGone.setVisibility(View.VISIBLE)
            holder.undoButton.setVisibility(View.VISIBLE)

            holder.undoButton.setOnClickListener {
                val pendingRemovalRunnable = pendingRunnables[taskAtual]
                pendingRunnables.remove(taskAtual)
                if (pendingRemovalRunnable != null) {
                    handler.removeCallbacks(pendingRemovalRunnable)
                }
                itemsPendingRemoval.remove(taskAtual)
                notifyItemChanged(tasks.indexOf(taskAtual))
            }

        }else {

            holder.nameTask.setText(taskAtual.name)

            holder.layoutNormal.setVisibility(View.VISIBLE)
            holder.layoutGone.setVisibility(View.GONE)
            holder.undoButton.setVisibility(View.GONE)
            holder.undoButton.setOnClickListener(null)

            if (taskAtual.finished){
                holder.nameTask.isChecked = true
            }
            //mudar no banco o finished
            if(holder.nameTask.isChecked){
                //holder.nameTask.isChecked = false
            }else{
                //holder.nameTask.isChecked = true
            }


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