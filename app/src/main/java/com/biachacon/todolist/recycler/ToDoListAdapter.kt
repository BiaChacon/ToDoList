package com.biachacon.todolist.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.edit_list_layout.view.*

class ToDoListAdapter (var c: Context, var toDoList:MutableList<ToDoList>) : RecyclerView.Adapter<ToDoListViewHolder>(){

    val db: AppDatabase by lazy {
        Room.databaseBuilder(c, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }
    var dialogview = View.inflate(c,R.layout.edit_list_layout,null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.todolist_inflater, parent, false)
        return ToDoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        var toDoListAtual = toDoList[position]

        holder.qtdTasks.text = toDoListAtual.qtd_taks.toString()

        if (toDoListAtual.qtd_taks <= 0) {
            holder.noTasks.visibility = View.VISIBLE
        }else{
            holder.layoutQtdTasks.visibility = View.VISIBLE
        }

        holder.nameToDoList.text = toDoListAtual.name

        if (position <= 0){
            holder.deleteBt.visibility = View.INVISIBLE
            holder.editBt.visibility = View.INVISIBLE
        }else{
            holder.deleteBt.visibility = View.VISIBLE
            holder.editBt.visibility = View.VISIBLE
        }

        // n entendo o que de não estã fucionando
        holder.editBt.setOnClickListener {
            //aparecer dialog para editar
            // isso aqui é pra aparecer o nome no edit text
            var dialogview = View.inflate(c,R.layout.edit_list_layout,null)
            dialogview.nameEditList.setText(toDoListAtual.name)

            var alert= AlertDialog.Builder(c)
            alert.setView(R.layout.edit_list_layout)

            alert.setPositiveButton("SIM",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    //pego o que a pessoa digitou e coloco no objeto
                    toDoListAtual.name = dialogview.nameEditList.text.toString()
                    //altero no banco
                    db.toDoListDao().update(toDoListAtual)
                    Toast.makeText(c,"Alterado",Toast.LENGTH_SHORT).show()
                    //digo que mudei um item
                    notifyItemChanged(position)

                })
            alert.setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,"Cancelado",Toast.LENGTH_SHORT).show()
                })

            var dialog = alert.create()
            dialog.show()
        }

        holder.deleteBt.setOnClickListener {
            var alert= AlertDialog.Builder(c)
            alert.setMessage("Deletar Permanentemente?")
            alert.setPositiveButton("SIM",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    toDoList.remove(toDoListAtual)
                    notifyItemRemoved(position)

                    var tasks = db.taskDao().findByToDoList(toDoListAtual.id)
                    for (i in tasks){
                        db.taskDao().delete(i)
                    }
                    db.toDoListDao().delete(toDoListAtual)


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
            notifyItemChanged(position)
        }

    }

}