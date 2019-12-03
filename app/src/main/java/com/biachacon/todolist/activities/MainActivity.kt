package com.biachacon.todolist.activities

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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
import androidx.cursoradapter.widget.CursorAdapter



class MainActivity : AppCompatActivity(), AddListDialogFragment.NoticeDialogListener{

    lateinit var pageAdapter:FixedTabsPageAdapter

    val db:AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "to-do-list")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var tasks:Array<Task>
    lateinit var taskAdapter:ArrayAdapter<String>

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

        taskAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_expandable_list_item_1,
            names)

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

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

//        val searchItem = menu.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.queryHint = getString(R.string.search)
//        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1
//        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
//
//        val to = intArrayOf(R.id.item_label)
//        val cursorAdapter = SimpleCursorAdapter(this, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
//        val suggestions = listOf("111111", "222222", "3333333", "4444444")
//
//
////        searchView.suggestionsAdapter = cursorAdapter
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                searchView.setQuery("",false)
//                searchItem.collapseActionView()
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
//                query?.let {
//                    suggestions.forEachIndexed { index, suggestion ->
//                        if (suggestion.contains(query, true))
//                            cursor.addRow(arrayOf(index, suggestion))
//                    }
//                }
//                cursorAdapter.changeCursor(cursor)
//                return true
//            }
//        })
//
//        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
//            override fun onSuggestionSelect(position: Int): Boolean {
//                return false
//            }
//
//            override fun onSuggestionClick(position: Int): Boolean {
//                searchView.clearFocus()
//                searchView.setQuery("",false)
//                searchItem.collapseActionView()
//                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
//                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
//                searchView.setQuery(selection, false)
//                return true
//            }
//        })
//
//
//
//
//






//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu, menu)
//
//        val  manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchItem = menu?.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                searchView.setQuery("",false)
//                searchItem.collapseActionView()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                Toast.makeText(this@MainActivity, "Looking for $newText", Toast.LENGTH_LONG).show()
//                return false
//            }
//        })
//
//        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener{
//            override fun onSuggestionClick(position: Int): Boolean {
//                val ca = searchView.suggestionsAdapter
//                val cursor = ca.cursor
//                cursor.moveToPosition(position)
//                searchView.setQuery(cursor.getString(cursor.getColumnIndex("task")), false)
//                return true
//            }
//
//            override fun onSuggestionSelect(position: Int): Boolean {
//                return true
//            }
//        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

//        if (id == R.id.action_search) {
//
//           SearchView(this).apply {
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
//            return true
//        }

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
                        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
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
        intent.putExtra("newTask", 1)
        startActivityForResult(intent, CODE)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        var editText = dialog.dialog.findViewById<EditText>(R.id.nameList)
        var newList = editText.text.toString()
        if (!newList.isEmpty()) {
            var t = ToDoList(newList)
            var r = db.toDoListDao().findByName("Invisivel02122019")
            if (r != null) {
                db.toDoListDao().delete(r)
            }
            var list = ToDoList("Invisivel02122019")
            db.toDoListDao().insert(t)
            db.toDoListDao().insert(list)
            pageAdapter.f2.onResume()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this,R.string.canceled,Toast.LENGTH_SHORT).show()
    }

}
