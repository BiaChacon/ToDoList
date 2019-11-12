package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase

/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : Fragment() {
//
//    val db:AppDatabase by lazy {
//        Room.databaseBuilder(this!!.activity!!, AppDatabase::class.java, "to-do-list")
//            .allowMainThreadQueries()
//            .build()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment1, container, false)
    }

}
