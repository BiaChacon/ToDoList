package com.biachacon.todolist.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R

class ToDoListViewHolder(v : View) : RecyclerView.ViewHolder(v)  {

    val nameToDoList: TextView
    val qtdTasks: TextView
    val editBt: ImageButton
    val deleteBt: ImageButton

    init {
        nameToDoList = v.findViewById(R.id.nameToDoList)
        qtdTasks = v.findViewById(R.id.qtdTasks)
        editBt = v.findViewById(R.id.editBt)
        deleteBt = v.findViewById(R.id.deleteBt)
    }

}
