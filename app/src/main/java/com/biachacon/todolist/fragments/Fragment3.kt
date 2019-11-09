package com.biachacon.todolist.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biachacon.todolist.R
import kotlinx.android.synthetic.main.layout_fragment3.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fab3.setOnClickListener {

        }

        return inflater.inflate(R.layout.layout_fragment3, container, false)
    }


}
