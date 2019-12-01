package com.biachacon.todolist.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    val CODE = 99
    lateinit var tasks:MutableList<Task>
    lateinit var adapter:Task2Adapter
    lateinit var  rv: RecyclerView
    val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        var param = intent.extras
        id = param?.getInt("list")!!

        val actionBar = supportActionBar
        actionBar!!.title = db.toDoListDao().findById(id.toLong()).name

    }

    override fun onResume() {
        super.onResume()

        tasks = db.taskDao().findByToDoList(id)

        adapter = this.let { Task2Adapter(it,tasks) }

        rv = this.findViewById(R.id.recyclerview)

        rv.adapter = adapter

        rv.layoutManager = layout

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        onResume()
                        Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED->{
                        Toast.makeText(this, R.string.canceled , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun addTask2(view: View) {
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra("newTask", id)
        startActivityForResult(intent, CODE)
    }

}
