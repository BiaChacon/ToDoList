package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biachacon.todolist.R
import kotlinx.android.synthetic.main.layout_fragment2.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fab2.setOnClickListener {

        }

        return inflater.inflate(R.layout.layout_fragment2, container, false)
    }


}
