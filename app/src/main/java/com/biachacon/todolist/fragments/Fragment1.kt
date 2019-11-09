package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : Fragment() {

    val CODE = 99

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
/*
        fab1.setOnClickListener {
            val intent = Intent(activity, AddTaskActivity::class.java)
            startActivityForResult(intent, CODE)
        }
*/
        return inflater.inflate(com.biachacon.todolist.R.layout.layout_fragment1, container, false)
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        Toast.makeText(activity, "SAVE", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED->{
                        Toast.makeText(activity, "CANCELED" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
*/
}
