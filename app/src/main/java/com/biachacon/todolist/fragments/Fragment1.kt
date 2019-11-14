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
import com.biachacon.todolist.model.Task
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
        v =  inflater.inflate(R.layout.layout_fragment2, container, false)
        return v
    }

    override fun onResume() {
        super.onResume()

        var tasks:MutableList<Task> = db.taskDao().findByNotFinished()

        var adapter = activity?.let { TaskAdapter(it,tasks) }

        var  rv: RecyclerView = v!!.findViewById(R.id.recyclerview2)

        rv.adapter = adapter

        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layout

        recyclerview1.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val positionView = (recyclerview1.getLayoutManager() as LinearLayoutManager).findFirstVisibleItemPosition()

                if (positionView > 0) {
                    if(!fab.isShown) {
                        fab.show()
                    }
                } else  {
                    if(fab.isShown) {
                        fab.hide()
                    }
                }
            }
        })

    }

}
