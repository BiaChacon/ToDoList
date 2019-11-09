package com.biachacon.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class ToDoList (
    var name:String,
    var qtd_taks:Int = 0,
    var taks: MutableList<Task>,
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
)