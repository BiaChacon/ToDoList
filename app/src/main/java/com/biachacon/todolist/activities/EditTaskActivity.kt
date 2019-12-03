package com.biachacon.todolist.activities

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.biachacon.todolist.dialogs.ConfirmExitWOSave
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*

class EditTaskActivity : AppCompatActivity(), AddListDialogFragment.NoticeDialogListener,
    ConfirmExitWOSave.ConfirmeExitWOSaveListener {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    var  c :Boolean?=null
    var dialog = ConfirmExitWOSave()
    var l: String = ""
    var id:Int = 0

    lateinit var toDoList:Array<ToDoList>
    var size = 0
    lateinit var list:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.edit_task)

        var param = intent.extras
        id = param?.getInt("task")!!

        var t = db.taskDao().findById(id.toLong())

        var tl: ToDoList = db.toDoListDao().findById(t.id_ToDoList.toLong())
        l = tl.name
        listChoiceE.setText(l)
        editTask.setText(t.name)
        dateChoiceE.setText(t.date)

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

        listChoiceE.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            var p = 0
            builder.setItems(list) { dialog, which ->
                for (i in list){
                    when (which) {
                        p -> {listChoiceE.setText(list.get(which))}
                    }
                    p++
                }
            }
            l = listChoiceE.text.toString()
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

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        var t = ToDoList(newList)
        db.toDoListDao().insert(t)
        onResume()
        listChoiceE.setText(t.name)
        l = t.name
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,R.string.canceled, Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()

        toDoList = db.toDoListDao().listAll()
        size = toDoList.size
        list = Array<String>(size-1, {i -> i.toString()})

        for (t in 0 until  toDoList.size-1){
            list[t] = toDoList[t].name
        }

    }

    fun editTask(view: View) {
        var task = db.taskDao().findById(id!!.toLong())
        task.name = editTask.text.toString()
        task.date = dateChoiceE.text.toString()

        l = listChoiceE.text.toString()

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

}
