package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.recycler.MyRecyclerViewClickListener
import com.biachacon.todolist.recycler.TaskAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_fragment1.*

class Fragment1 : Fragment() {

    var v:View? = null

    val db:AppDatabase by lazy {
        Room.databaseBuilder(this!!.activity!!, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.layout_fragment1, container, false)
        return v
    }

    override fun onResume() {
        super.onResume()

        var tasks:MutableList<Task> = db.taskDao().findByNotFinished()

        var adapter = activity?.let { TaskAdapter(it,tasks) }

        var  rv: RecyclerView = v!!.findViewById(R.id.recyclerview1)

        rv.adapter = adapter

        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layout

        recyclerview1.addOnItemTouchListener(
            MyRecyclerViewClickListener(
                this!!.activity!!,
                recyclerview1,
                object : MyRecyclerViewClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        Toast.makeText(activity, "Clique simples", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
        )

    }

}
