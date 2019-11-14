package com.biachacon.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    var name:String,
    var date: String,
    var finished: Boolean = false,
    var id_ToDoList:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}