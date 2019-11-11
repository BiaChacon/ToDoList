package com.biachacon.todolist.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.biachacon.todolist.R
import com.biachacon.todolist.adapaters.FixedTabsPageAdapter
import com.biachacon.todolist.fragments.AddListDialogFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fab.setOnClickListener {}


        setContentView(R.layout.activity_main)
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

        if (id == R.id.action_one) {
            Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.action_two) {
            var dialog = AddListDialogFragment()
            dialog.show(supportFragmentManager, "Dialog")
            return true
        }
        if (id == R.id.action_three) {
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
                        Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED->{
                        Toast.makeText(this, "CANCELED" , Toast.LENGTH_SHORT).show()
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
