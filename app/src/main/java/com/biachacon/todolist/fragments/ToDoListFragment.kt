package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.ToDoList
import com.biachacon.todolist.adapaters.ToDoListAdapter

class ToDoListFragment (var tasksFragment: TasksFragment): Fragment() {

    var v:View? = null
    var adapter: ToDoListAdapter? = null

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this!!.activity!!, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.layout_fragment2, container, false)
        return v
    }

    override fun onResume() {
        super.onResume()

        var toDoList:MutableList<ToDoList> = db.toDoListDao().list()

        adapter = activity?.let {
            ToDoListAdapter(
                it,
                toDoList,
                tasksFragment
            )
        }

        var  rv:RecyclerView = v!!.findViewById(R.id.recyclerview2)

        rv.adapter = adapter

        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layout

    }

}
