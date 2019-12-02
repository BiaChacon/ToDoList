package com.biachacon.todolist.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.activities.EditTaskActivity
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList

class TaskAdapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>()
    /*,ConfirmFinishedDialog.NoticeDialogListener,ConfirmDeleteDialog.NoticeDialogListener*/ {

    //variável para saber de foi confimado no dialog
    var confirmed = false
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

        if(position == itemCount-1){
            holder.cardT.visibility = View.INVISIBLE
        }

        holder.nameTask.text = taskAtual.name
        holder.dateTask.text = taskAtual.date

        var t =  db.toDoListDao().findById(taskAtual.id_ToDoList.toLong())
        holder.nameList.text = t.name

        if(taskAtual.date != "")
            holder.dateTask.visibility = View.VISIBLE

        if(holder.nameList.text != c.getString(R.string.name_default))
            holder.nameList.visibility = View.VISIBLE

        if(taskAtual.finished){
            holder.finished.isChecked = true
            stringDialog = c.getString(R.string.undo)
        }else{
            stringDialog = c.getString(R.string.finished)
        }

        holder.layoutTask.setOnClickListener{
            var intent = Intent(c,EditTaskActivity::class.java)
           intent.putExtra("task", taskAtual.id)
            c.startActivity(intent)
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
                        tasks.remove(taskAtual)
                        notifyItemRemoved(position)
                        db.taskDao().update(taskAtual)
                        //Toast.makeText(c,"Desfeita",Toast.LENGTH_SHORT).show()

                    }else{
                        taskAtual.finished = true
                        tasks.remove(taskAtual)
                        notifyItemRemoved(position)
                        db.taskDao().update(taskAtual)
                        //Toast.makeText(c,"Finalizada",Toast.LENGTH_SHORT).show()

                    }
                })
            alert.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,R.string.canceled,Toast.LENGTH_SHORT).show()
                    holder.finished.isChecked = stringDialog == c.getString(R.string.undo)
                })

//            if(taskAtual.finished){
//                taskAtual.finished = false
//                tasks.remove(taskAtual)
//                notifyItemRemoved(position)
//                db.taskDao().update(taskAtual)
//            }else{
//                taskAtual.finished = true
//                tasks.remove(taskAtual)
//                notifyItemRemoved(position)
//                db.taskDao().update(taskAtual)
//            }
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

//            tasks.remove(taskAtual)
//            notifyItemRemoved(position)
//            db.taskDao().delete(taskAtual)
            var dialog = alert.create()
            dialog.show()
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