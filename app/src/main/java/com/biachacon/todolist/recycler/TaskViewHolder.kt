package com.biachacon.todolist.recycler

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R

class TaskViewHolder(v : View) : RecyclerView.ViewHolder(v) {

    val nameTask: CheckBox
    val dateTask: TextView
    val nameToDoList: TextView

    val layoutNormal: LinearLayout
    val layoutGone: LinearLayout
    val undoButton: Button

    init {
        nameTask = v.findViewById(R.id.nameTask)
        dateTask = v.findViewById(R.id.dateTask)
        nameToDoList = v.findViewById(R.id.nameToDoList)
        layoutNormal = v.findViewById(R.id.layout_normal)
        layoutGone = v.findViewById(R.id.layout_gone)
        undoButton = v.findViewById(R.id.undo_button)
    }
}