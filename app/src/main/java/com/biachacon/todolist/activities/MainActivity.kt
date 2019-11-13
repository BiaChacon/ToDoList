package com.biachacon.todolist.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.biachacon.todolist.R
import com.biachacon.todolist.adapaters.FixedTabsPageAdapter
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.dialogs.AddListDialogFragment
import com.biachacon.todolist.model.ToDoList
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val CODE = 99

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this, AppDatabase::class.java,
            "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var l1 = ToDoList("test1")
//        var l2 = ToDoList("test2")
//        var l3 = ToDoList("test3")
//        db.toDoListDao().insert(l1)
//        db.toDoListDao().insert(l2)
//        db.toDoListDao().insert(l3)
//        Log.i("teste", "${db.toDoListDao().listAll().size}")

        if(getSupportActionBar() != null)
        {
            getSupportActionBar()!!.setElevation(0.0F)
        }

        val pageAdapter =
            FixedTabsPageAdapter(supportFragmentManager)

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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.search) {
            Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.newList) {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
            return true
        }
        if (id == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE ->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED->{
                        Toast.makeText(this, "canceled" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun addTask(view: View) {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivityForResult(intent, CODE)
    }

}
