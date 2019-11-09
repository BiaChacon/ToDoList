package com.biachacon.todolist.dao

import androidx.room.*
import com.biachacon.todolist.model.ToDoList

@Dao
interface ToDoListDao {

    @Insert
    fun insert(toDoList: ToDoList): Long

    @Delete
    fun delete(toDoList: ToDoList): Int

    @Update
    fun update(toDoList: ToDoList): Int

    @Query("SELECT * FROM ToDoList")
    fun listAll(): Array<ToDoList>

    @Query("SELECT * FROM toDoList WHERE id = :id")
    fun findById(id: Long): ToDoList

    @Query("SELECT * FROM toDoList WHERE name = :name")
    fun findByName (name: String) : ToDoList

    @Query("SELECT * FROM toDoList")
    fun list(): MutableList<ToDoList>

}