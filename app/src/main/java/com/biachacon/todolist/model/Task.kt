package com.biachacon.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Task (
    var name:String,
    var date: Date,
    var finished: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
)