package com.biachacon.todolist.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import com.biachacon.todolist.viewholder.TaskViewHolder

class Task2Adapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>(){

    var stringDialog: String = ""

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

        if(taskAtual.date != "")
            holder.dateTask.visibility = View.VISIBLE

        if(taskAtual.finished){
            holder.finished.isChecked = true
            stringDialog = c.getString(R.string.undo)
        }else{
            stringDialog = c.getString(R.string.finished)
        }

        holder.finished.setOnClickListener {
            var alert= AlertDialog.Builder(c)
            alert.setTitle(R.string.are_you_sure)
            if(taskAtual.finished){
                alert.setMessage(R.string.task_to_redo)
            }else{
                alert.setMessage(R.string.finish_task)
            }

            alert.setPositiveButton(R.string.yes,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    if(taskAtual.finished){
                        taskAtual.finished = false
                        notifyDataSetChanged()
                        db.taskDao().update(taskAtual)
                        //Toast.makeText(c,"Desfeita",Toast.LENGTH_SHORT).show()

                    }else{
                        taskAtual.finished = true
                        notifyDataSetChanged()
                        db.taskDao().update(taskAtual)
                        //Toast.makeText(c,"Finalizada",Toast.LENGTH_SHORT).show()

                    }
                })
            alert.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,R.string.canceled,Toast.LENGTH_SHORT).show()
                    holder.finished.isChecked = stringDialog == c.getString(R.string.undo)
                })

            var dialog = alert.create()
            dialog.show()
        }

        holder.deleteTask.setOnClickListener {

            var alert= AlertDialog.Builder(c)
            alert.setTitle(R.string.are_you_sure)
            alert.setMessage(R.string.delete_task)
            alert.setPositiveButton(R.string.delete,
                DialogInterface.OnClickListener { dialogInterface, i ->

                    var toDoList:ToDoList = db.toDoListDao().findById(taskAtual.id_ToDoList.toLong())
                    toDoList.qtd_taks--
                    db.toDoListDao().update(toDoList)

                    db.taskDao().delete(taskAtual)

                    tasks.remove(taskAtual)
                    notifyDataSetChanged()

                    Toast.makeText(c,R.string.task_deleted,Toast.LENGTH_SHORT).show()

                })

            alert.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,R.string.canceled,Toast.LENGTH_SHORT).show()
                })

            var dialog = alert.create()
            dialog.show()

        }

    }

}