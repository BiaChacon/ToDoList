package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biachacon.todolist.R
import kotlinx.android.synthetic.main.layout_fragment1.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fab1.setOnClickListener {

        }

        return inflater.inflate(R.layout.layout_fragment1, container, false)
    }


}
