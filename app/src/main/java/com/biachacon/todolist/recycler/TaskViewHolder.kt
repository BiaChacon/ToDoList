package com.biachacon.todolist.recycler

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R

class TaskViewHolder(v : View) : RecyclerView.ViewHolder(v) {

    val finished: CheckBox
    val nameTask: TextView
    val dateTask: TextView
    val nameList: TextView
    val deleteTask: ImageButton
    val layoutTask: LinearLayout
    init {
        nameTask = v.findViewById(R.id.nameTask)
        dateTask = v.findViewById(R.id.dateTask)
        nameList = v.findViewById(R.id.nameListT)
        deleteTask = v.findViewById(R.id.deleteTask)
        finished = v.findViewById(R.id.checkboxTask)
        layoutTask = v.findViewById(R.id.layoutTask)
    }

}