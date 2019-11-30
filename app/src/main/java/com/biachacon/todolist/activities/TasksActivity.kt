package com.biachacon.todolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.recycler.Task2Adapter

class TasksActivity : AppCompatActivity() {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        var param = intent.extras
        id = param?.getInt("list")!!

        val actionBar = supportActionBar
        actionBar!!.title = db.toDoListDao().findById(id.toLong()).name

        var tasks:MutableList<Task> = db.taskDao().findByToDoList(id)

        var adapter = this.let { Task2Adapter(it,tasks) }

        var  rv: RecyclerView = this.findViewById(R.id.recyclerview)

        rv.adapter = adapter

        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layout
    }

}
