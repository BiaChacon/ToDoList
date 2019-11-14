package com.biachacon.todolist.activities

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.AddListDialogFragment
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var list:Array<String?>
    var l: String = ""
    var date:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val actionBar = supportActionBar
        actionBar!!.title = "New Task"

        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datePicker.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                dateChoice.setText(""+ mDay+"/"+mMonth+"/"+mYear)
                date = ""+mDay+"/"+mMonth+"/"+mYear
            }, year, month, day)
            dpd.show()
        }
        dateChoice.setKeyListener(null);
        dateChoice.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                dateChoice.setText(""+ mDay+"/"+mMonth+"/"+mYear)
                date = ""+mDay+"/"+mMonth+"/"+mYear
            }, year, month, day)
            dpd.show()
        }


        list = Array(db.toDoListDao().listAll().size) { null }
        var j=0
        for (i in db.toDoListDao().listAll()){
            list.set(j, value = i.name)
            j++
        }
        spinner!!.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(array_adapter)

        addNewlist.setOnClickListener {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        return when(id){
            R.id.cancel -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        l = list[position]!!
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    fun saveTask(view: View) {
        var toDoList:ToDoList = db.toDoListDao().findByName(l)
        toDoList.qtd_taks++
        db.toDoListDao().update(toDoList)
        db.taskDao().insert(Task(
            addTask.text.toString(),
            date,
            false,
            toDoList.id
        ))
        setResult(Activity.RESULT_OK)
        finish()
    }

}
