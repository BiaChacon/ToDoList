package com.biachacon.todolist.recycler

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R

class TaskViewHolder(v : View) : RecyclerView.ViewHolder(v) {

    val finished: CheckBox
    val nameTask: TextView
    val dateTask: TextView
    val nameToDoList: TextView
    val layoutNormal: LinearLayout
    val deleteTask: ImageButton

    init {
        nameTask = v.findViewById(R.id.nameTask)
        dateTask = v.findViewById(R.id.dateTask)
        nameToDoList = v.findViewById(R.id.nameToDoList)
        layoutNormal = v.findViewById(R.id.layout_normal)
        deleteTask = v.findViewById(R.id.deleteTask)
        finished = v.findViewById(R.id.checkboxTask)
    }
}