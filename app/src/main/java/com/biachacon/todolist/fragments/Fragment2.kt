package com.biachacon.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.biachacon.todolist.R
import com.biachacon.todolist.database.AppDatabase
import com.biachacon.todolist.model.Task
import com.biachacon.todolist.model.ToDoList
import com.biachacon.todolist.recycler.MyRecyclerViewClickListener
import com.biachacon.todolist.recycler.TaskAdapter
import com.biachacon.todolist.recycler.ToDoListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_fragment2.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment2 : Fragment() {
//
//    val db: AppDatabase by lazy {
//        Room.databaseBuilder(this!!.activity!!, AppDatabase::class.java, "to-do-list")
//            .allowMainThreadQueries()
//            .build()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment2, container, false)
    }

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        var toDoList:MutableList<ToDoList> = db.toDoListDao().list()
//
//        var adapter = activity?.let { ToDoListAdapter(it,toDoList ) }
//        recyclerview2.adapter = adapter
//
//        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//
//        recyclerview2.layoutManager = layout

//        recyclerview.addOnItemTouchListener(
//            MyRecyclerViewClickListener(
//                this,
//                recyclerview,
//                object : MyRecyclerViewClickListener.OnItemClickListener {
//                    override fun onItemClick(view: View, position: Int) {
//                        Toast.makeText(this@RecyclerViewActivity, "Clique simples", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val removida = listaLivros[position]
//                        listaLivros.remove(removida)
//                        recyclerview.adapter!!.notifyItemRemoved(position)
//                        Toast.makeText(this@RecyclerViewActivity, "Clique longo", Toast.LENGTH_SHORT).show()
//                        val snack = Snackbar.make(
//                            recyclerview.parent as View,"Removido", Snackbar.LENGTH_LONG )
//                            .setAction("Cancelar") {
//                                listaLivros.add(position, removida)
//                                recyclerview.adapter!!.notifyItemInserted(position)
//                            }
//                        snack.show()
//                    }
//                })
//        )
//    }

}
