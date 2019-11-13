package com.biachacon.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biachacon.todolist.dao.TaskDao
import com.biachacon.todolist.dao.ToDoListDao
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList

@Database(
    entities = [Task::class,ToDoList::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
    abstract fun toDoListDao(): ToDoListDao
}