package com.biachacon.todolist.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biachacon.todolist.R
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        addBt.setOnClickListener {

            //colocar pra salvar no banco

            setResult(Activity.RESULT_OK)
            finish()
        }

        canceledBt.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

}
