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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.AddListDialogFragment
import com.biachacon.todolist.dialogs.ConfirmExitWOSave
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.add_list_layout.*
import java.util.*

class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AddListDialogFragment.NoticeDialogListener,ConfirmExitWOSave.ConfirmeExitWOSaveListener{


    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    var  c :Boolean?=null
    lateinit var list:Array<String?>
    var l: String = ""
    var date:String = ""

    var dialog = ConfirmExitWOSave()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        c = false
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


        listSpinnir()


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
                dialog.show(supportFragmentManager,"DialogWOSave")
                if (c == true) {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
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

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        var t = ToDoList(newList)
        db.toDoListDao().insert(t)
        listSpinnir()

    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,"Cancelada",Toast.LENGTH_SHORT).show()
    }

    override fun onQuitAnyway(dialog: DialogFragment) {
        super.onBackPressed()
        c = true
    }
    override fun onCancelQuit(dialog: DialogFragment) {
        c = false
    }

    override fun onBackPressed() {
        dialog.show(supportFragmentManager,"DialogWOSave")
    }

    fun listSpinnir(){
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

    }

}
