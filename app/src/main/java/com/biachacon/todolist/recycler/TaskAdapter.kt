package com.biachacon.todolist.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.dao.ToDoListDao
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.ConfirmDeleteDialog
import com.biachacon.todolist.dialogs.ConfirmFinishedDialog
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList

class TaskAdapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>()
    /*,ConfirmFinishedDialog.NoticeDialogListener,ConfirmDeleteDialog.NoticeDialogListener*/ {

    //variável para saber de foi confimado no dialog
    var confirmed = false

    val db: AppDatabase by lazy {
        Room.databaseBuilder(c, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }
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
        var t =  db.toDoListDao().findById(taskAtual.id_ToDoList.toLong())
        holder.nameList.text = t.name

        if(taskAtual.finished){
            holder.finished.isChecked = true
        }

        holder.finished.setOnClickListener {
            if(taskAtual.finished){
                taskAtual.finished = false
                tasks.remove(taskAtual)
                notifyItemRemoved(position)
                db.taskDao().update(taskAtual)
            }else{
                taskAtual.finished = true
                tasks.remove(taskAtual)
                notifyItemRemoved(position)
                db.taskDao().update(taskAtual)
            }
        }

        holder.deleteTask.setOnClickListener {
            tasks.remove(taskAtual)
            notifyItemRemoved(position)
            db.taskDao().delete(taskAtual)
        }



            //showNoticeDialog()
            //if(confirmed){
                //remove da lista atual e coloca na lista de finalizadas
               // notifyItemRemoved(position)
            //}


        //holder.deleteTask.setOnClickListener {
            //showNoticeDialog()
            //if (confirmed){
                //deleta a task do banco
                //notifyItemRemoved(position)
            //}
        //}


       /* if (taskAtual.finished){
            holder.finished.isChecked = true
        }*/

    }


//
//    fun showNoticeDialog() {
//        // Create an instance of the dialog fragment and show it
//        val dialog = ConfirmFinishedDialog()
//        dialog.show(FragmentActivity().supportFragmentManager, "FinishedDialog")
//    }

//    override fun onDialogPositiveClick(dialog: DialogFragment) {
//        confirmed =true
//    }
//
//    override fun onDialogNegativeClick(dialog: DialogFragment) {
//        confirmed = false
//    }

//    override fun onDialogPositiveClickD(dialog: DialogFragment) {
//        confirmed = true
//    }
//
//    override fun onDialogNegativeClickD(dialog: DialogFragment) {
//        confirmed = false
//    }

}