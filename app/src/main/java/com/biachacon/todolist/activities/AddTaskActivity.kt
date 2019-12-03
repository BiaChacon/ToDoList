package com.biachacon.todolist.activities

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.AddListDialogFragment
import com.biachacon.todolist.dialogs.AddTextDialog
import com.biachacon.todolist.dialogs.ConfirmExitWOSave
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity(),AddListDialogFragment.NoticeDialogListener,
    ConfirmExitWOSave.ConfirmeExitWOSaveListener{

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    var  c :Boolean?=null
    var l: String = ""
    var date:String = ""

    var dialog = ConfirmExitWOSave()
    var dialogAddText = AddTextDialog()

    lateinit var toDoList:Array<ToDoList>
    var size = 0
    lateinit var list:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        c = false

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.add_task)

        var param = intent.extras
        var id = param?.getInt("newTask")!!
        var nameList = db.toDoListDao().findById(id.toLong()).name

        listChoice.setText(nameList)
        l = nameList

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

        dateChoice.setKeyListener(null)
        dateChoice.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                dateChoice.setText(""+ mDay+"/"+mMonth+"/"+mYear)
                date = ""+mDay+"/"+mMonth+"/"+mYear
            }, year, month, day)
            dpd.show()
        }

        addNewlist.setOnClickListener {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
        }
        listChoice.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            var p = 0
            builder.setItems(list) { dialog, which ->
                for (i in list){
                    when (which) {
                       p -> {listChoice.setText(list.get(which))}
                    }
                    p++
                }
            }
            val dialog = builder.create()
            dialog.show()
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
                if (somethingNotChanged()){
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }else {
                    dialog.show(supportFragmentManager, "DialogWOSave")
                }
                if (c == true) {
                setResult(Activity.RESULT_CANCELED)
                finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        if(!newList.isEmpty()) {
            var t = ToDoList(newList)
            var r = db.toDoListDao().findByName("Invisivel02122019")
            if (r != null) {
                db.toDoListDao().delete(r)
            }
            var list = ToDoList("Invisivel02122019")
            db.toDoListDao().insert(t)
            db.toDoListDao().insert(list)
            onResume()
            listChoice.setText(t.name)
            l = t.name
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,R.string.canceled,Toast.LENGTH_SHORT).show()
    }

    override fun onQuitAnyway(dialog: DialogFragment) {
        super.onBackPressed()
        c = true
    }

    override fun onCancelQuit(dialog: DialogFragment) {
        c = false
    }
    fun somethingNotChanged():Boolean{
        return addTask.text.isEmpty() && dateChoice.text.isEmpty()
    }
    override fun onBackPressed() {
        if(somethingNotChanged()) {
            super.onBackPressed()
        }else{
            dialog.show(supportFragmentManager, "DialogWOSave")
        }
    }

    override fun onResume() {
        super.onResume()

        toDoList = db.toDoListDao().listAll()
        size = toDoList.size
        list = Array<String>(size-1, {i -> i.toString()})

        for (t in 0 until  toDoList.size-1){
            list[t] = toDoList[t].name
        }

    }

    fun saveTask(view: View) {
        if(addTask.text.isEmpty()){
            dialogAddText.show(supportFragmentManager,"DialogAddText")
        }else {
            var tb = db.taskDao().findByName("Invisivel02122019")

            if (tb != null) {
                db.taskDao().delete(tb)
            }

            l = listChoice.text.toString()
            var toDoList: ToDoList = db.toDoListDao().findByName(l)
            toDoList.qtd_taks++
            db.toDoListDao().update(toDoList)

            db.taskDao().insert(
                Task(
                    addTask.text.toString(),
                    date,
                    false,
                    toDoList.id
                )
            )

            var t = Task("Invisivel02122019", "", false, toDoList.id)
            db.taskDao().insert(t)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

}
