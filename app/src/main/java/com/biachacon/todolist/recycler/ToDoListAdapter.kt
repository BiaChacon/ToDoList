package com.biachacon.todolist.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.activities.TasksActivity
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

        holder.layoutList.setOnClickListener{
            var intent = Intent(c, TasksActivity::class.java)
            intent.putExtra("list", toDoListAtual.id)
            c.startActivity(intent)
        }

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


        holder.editBt.setOnClickListener {
            dialogview.nameEditList.setText(toDoListAtual.name)

            var alert= AlertDialog.Builder(c)
            alert.setView(dialogview)

            alert.setPositiveButton(R.string.save,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    toDoListAtual.name = dialogview.nameEditList.text.toString()
                    db.toDoListDao().update(toDoListAtual)
                    Toast.makeText(c,R.string.updated,Toast.LENGTH_SHORT).show()
                    notifyItemChanged(position)
                }
            )
            alert.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,R.string.canceled,Toast.LENGTH_SHORT).show()
                }
            )

            var dialog = alert.create()
            dialog.show()

        }

        holder.deleteBt.setOnClickListener {

            var alert= AlertDialog.Builder(c)

            alert.setTitle(R.string.are_you_sure)
            alert.setMessage(R.string.list_deleted_message)
            alert.setPositiveButton(R.string.delete,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    var tasks = db.taskDao().findByToDoList(toDoListAtual.id)
                    for (i in tasks){
                        db.taskDao().delete(i)
                    }
                    db.toDoListDao().delete(toDoListAtual)
                    toDoList.remove(toDoListAtual)
                    notifyDataSetChanged()
                    Toast.makeText(c,R.string.list_deleted,Toast.LENGTH_SHORT).show()
                }
            )
            alert.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(c,R.string.canceled,Toast.LENGTH_SHORT).show()
                }
            )

            var dialog = alert.create()
            dialog.show()

        }

    }

}