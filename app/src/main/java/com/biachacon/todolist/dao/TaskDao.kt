package com.biachacon.todolist.dao

import androidx.room.*
import com.biachacon.todolist.model.Task

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task): Long

    @Delete
    fun delete(task: Task): Int

    @Update
    fun update(task: Task): Int

    @Query("SELECT * FROM task")
    fun listAll(): Array<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    fun findById(id: Long): Task

    @Query("SELECT * FROM task WHERE name = :name")
    fun findByName (name: String) : Task

    @Query("SELECT * FROM task")
    fun list(): MutableList<Task>

    @Query("SELECT * FROM task WHERE finished = 1 ")
    fun findByFinished(): MutableList<Task>

    @Query("SELECT * FROM task WHERE finished = 0")
    fun findByNotFinished(): MutableList<Task>

    @Query("SELECT * FROM task WHERE id_ToDoList = :id")
    fun findByToDoList(id: Int): MutableList<Task>

}
