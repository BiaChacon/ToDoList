package com.biachacon.todolist.recycler

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R

class ToDoListViewHolder(v : View) : RecyclerView.ViewHolder(v)  {

    val nameToDoList: TextView
    val qtdTasks: TextView
    val textTask: TextView

    val layoutNormal: LinearLayout
    val layoutGone: LinearLayout
    val undoButton: Button

    init {

        nameToDoList = v.findViewById(R.id.nameTask)
        qtdTasks = v.findViewById(R.id.qtdTasks)
        textTask = v.findViewById(R.id.textTask)

        layoutNormal = v.findViewById(R.id.layout_normal2)
        layoutGone = v.findViewById(R.id.layout_gone2)
        undoButton = v.findViewById(R.id.undo_button2)

    }
}
