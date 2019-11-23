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
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*

class EditTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AddListDialogFragment.NoticeDialogListener,ConfirmExitWOSave.ConfirmeExitWOSaveListener {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var list:Array<String?>
    var l: String = ""
    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val actionBar = supportActionBar
        actionBar!!.title = "Edit Task"

        var param = intent.extras
        id = param?.getInt("task")!!

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datePickerE.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dateChoiceE.setText(""+ mDay+"/"+mMonth+"/"+mYear)
            }, year, month, day)
            dpd.show()
        }
        dateChoiceE.setKeyListener(null);
        dateChoiceE.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dateChoiceE.setText(""+ mDay+"/"+mMonth+"/"+mYear)
            }, year, month, day)
            dpd.show()
        }

        addNewlistE.setOnClickListener {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
        }

        var task = db.taskDao().findById(id!!.toLong())
        editTask.setText(task.name)
        dateChoiceE.setText(task.date)
        //set list

        list = Array(db.toDoListDao().listAll().size) { null }
        var j=0
        for (i in db.toDoListDao().listAll()){
            list.set(j, value = i.name)
            j++
        }
        spinnerE!!.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerE!!.setAdapter(array_adapter)

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

    fun editTask(view: View) {
        var task = db.taskDao().findById(id!!.toLong())
        task.name = editTask.text.toString()
        task.date = dateChoiceE.text.toString()

        //set id list
        var toDoList: ToDoList = db.toDoListDao().findByName(l)
        var toDoList2: ToDoList = db.toDoListDao().findById(task.id_ToDoList.toLong())

        if (toDoList.id != task.id_ToDoList){
            toDoList.qtd_taks++
            db.toDoListDao().update(toDoList)
            toDoList2.qtd_taks--
            db.toDoListDao().update(toDoList2)
        }
        task.id_ToDoList = toDoList.id

        db.taskDao().update(task)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        var t = ToDoList(newList)
        db.toDoListDao().insert(t)

    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,"Cancelada", Toast.LENGTH_SHORT).show()
    }

    override fun onQuitAnyway(dialog: DialogFragment) {
        super.onBackPressed()
    }
    override fun onCancelQuit(dialog: DialogFragment) {
    }

    override fun onBackPressed() {
        var dialog = ConfirmExitWOSave()
        dialog.show(supportFragmentManager,"DialogWOSave")
    }

}
