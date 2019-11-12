package com.biachacon.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoList (var name:String){
    var taks: MutableList<Task>? = null
    var qtd_taks:Int = 0
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
