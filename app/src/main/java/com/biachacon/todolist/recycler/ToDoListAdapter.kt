package com.biachacon.todolist.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R
import com.biachacon.todolist.dialogs.EditListDialogFragment
import com.biachacon.todolist.model.ToDoList

class ToDoListAdapter (var c: Context, var toDoList:MutableList<ToDoList>) : RecyclerView.Adapter<ToDoListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.todolist_inflater, parent, false)
        return ToDoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {

        var toDoListAtual = toDoList[position]

        holder.qtdTasks.text = toDoListAtual.qtd_taks.toString()
        holder.nameToDoList.text = toDoListAtual.name

        holder.editBt.setOnClickListener {
            //aparecer dialog para editar
            notifyItemChanged(position)
        }

        holder.deleteBt.setOnClickListener {
            //mostrar dialog para confirmar delete
            notifyItemChanged(position)
        }

    }

}