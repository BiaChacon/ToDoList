package com.biachacon.todolist.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(foreignKeys = [
        ForeignKey(entity = ToDoList::class,
            parentColumns = ["id"],
            childColumns = ["idToDoList"],
            onDelete = CASCADE)])

data class Task (
    var name:String,
    var date: Date,
    var finished: Boolean = false
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}