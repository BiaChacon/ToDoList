package com.biachacon.todolist.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.opengl.Visibility
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

        holder.nameTask.text = taskAtual.name
        holder.dateTask.text = taskAtual.date

        var t =  db.toDoListDao().findById(taskAtual.id_ToDoList.toLong())
        holder.nameList.text = t.name

        if(taskAtual.date != "")
            holder.dateTask.visibility = View.VISIBLE

        if(holder.nameList.text != "Default")
            holder.nameList.visibility = View.VISIBLE

        if(taskAtual.finished){
            holder.finished.isChecked = true
            stringDialog = "Desfazer"
        }else{
            stringDialog = "Finalizar"
        }

        holder.finished.setOnClickListener {
            var alert= AlertDialog.Builder(c)
            alert.setMessage(stringDialog + " Tarefa?")
            alert.setPositiveButton("SIM",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    if(taskAtual.finished){
                        taskAtual.finished = false
                        tasks.remove(taskAtual)
                        notifyItemRemoved(position)
                        db.taskDao().update(taskAtual)
                        Toast.makeText(c,"Desfeita",Toast.LENGTH_SHORT).show()

                    }else{
                        taskAtual.finished = true
                        tasks.remove(taskAtual)
                        notifyItemRemoved(position)
                        db.taskDao().update(taskAtual)
                        Toast.makeText(c,"Finalizada",Toast.LENGTH_SHORT).show()

                    }
                })
            alert.setNegativeButton("NÃO",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,"Cancelado",Toast.LENGTH_SHORT).show()
                    holder.finished.isChecked = stringDialog == "Desfazer"
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
            alert.setMessage("Deletar Permanentemente?")
            alert.setPositiveButton("SIM",
                DialogInterface.OnClickListener { dialogInterface, i ->

                    tasks.remove(taskAtual)
                    notifyItemRemoved(position)

                    var toDoList:ToDoList = db.toDoListDao().findById(taskAtual.id_ToDoList.toLong())
                    toDoList.qtd_taks--
                    db.toDoListDao().update(toDoList)

                    db.taskDao().delete(taskAtual)

                    Toast.makeText(c,"Deletada",Toast.LENGTH_SHORT).show()

                })
            alert.setNegativeButton("NÃO",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,"Cancelado",Toast.LENGTH_SHORT).show()
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