package com.biachacon.todolist.activities

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.biachacon.todolist.R
import com.biachacon.todolist.adapaters.FixedTabsPageAdapter
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.AddListDialogFragment
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddListDialogFragment.NoticeDialogListener{

    lateinit var pageAdapter:FixedTabsPageAdapter

    val db:AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var tasks:Array<Task>
    lateinit var taskToListAdapter:ArrayAdapter<String>

    val searchManager: SearchManager by lazy {
        getSystemService(Context.SEARCH_SERVICE) as SearchManager
    }

    val CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var t = db.toDoListDao().listAll().size
        if (t <= 0) {
            db.toDoListDao().insert(
                ToDoList(getString(R.string.name_default))
            )
        }

        tasks = db.taskDao().listAll()
        var size = tasks.size
        var names = Array<String>(size, {i -> i.toString()})
        for (i in 0 until  tasks.size){
            names[i] = tasks[i].name
        }

        taskToListAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_expandable_list_item_1,
            names)

//        db.toDoListDao().insert(
//            ToDoList("Teste 1")
//        )
//        db.toDoListDao().insert(
//            ToDoList("Teste 2")
//        )
//        db.toDoListDao().insert(
//            ToDoList("Teste 3")
//        )

        if(getSupportActionBar() != null)
            getSupportActionBar()!!.setElevation(0.0F)

       pageAdapter =
            FixedTabsPageAdapter(supportFragmentManager,this)

        viewpager.adapter = pageAdapter

        tab.setupWithViewPager(viewpager)
        tab.getTabAt(0)
        tab.getTabAt(1)
        tab.getTabAt(2)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when (position) {
                    0 -> {
                        tab.getTabAt(0)
                        tab.getTabAt(1)
                        tab.getTabAt(2)
                    }
                    1 -> {
                        tab.getTabAt(0)
                        tab.getTabAt(1)
                        tab.getTabAt(2)
                    }
                    2 -> {
                        tab.getTabAt(0)
                        tab.getTabAt(1)
                        tab.getTabAt(2)
                    }
                    else -> return
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = false
            queryHint = getString(R.string.search)
            setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //Toast.makeText(this, "onQueryTextSubmit - " + query, Toast.LENGTH_LONG).show()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //Toast.makeText(this, "onQueryTextChange - " + newText, Toast.LENGTH_LONG).show()
                    return false
                }
            }
            )
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == R.id.action_search) {

//            SearchView(this).apply {
//                setIconifiedByDefault(true)
//                setSearchableInfo(searchManager.getSearchableInfo(componentName))
//                isSubmitButtonEnabled = false
//                queryHint = getString(R.string.search)
//
//                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String): Boolean {
//
//                        return false
//                    }
//
//                    override fun onQueryTextChange(newText: String): Boolean {
//                        taskToListAdapter.filter.filter(newText)
//                        return false
//                    }
//
//                })
//            }.also {
//                item.actionView = it
//            }

            return true

        }

        if (id == R.id.newList) {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
            return true
        }

//        if (id == R.id.settings) {
//            Toast.makeText(this, R.string.settings, Toast.LENGTH_LONG).show()
//            return true
//        }

//        if (id == R.id.about){
//            Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show()
//            return true
//        }

        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED->{
                        Toast.makeText(this, R.string.canceled , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun addTask(view: View) {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivityForResult(intent, CODE)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        var t = ToDoList(newList)
        db.toDoListDao().insert(t)
        Log.i("teste","chegou")
        pageAdapter.f2.onResume()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,"Cancelada",Toast.LENGTH_SHORT).show()
    }

}
