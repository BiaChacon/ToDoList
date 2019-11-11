package com.biachacon.todolist.recycler

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R
import com.biachacon.todolist.model.ToDoList
import java.util.*

class ToDoListAdapter (var c: Context, var toDoList:MutableList<ToDoList>) : RecyclerView.Adapter<TaskViewHolder>(){

    private val PENDING_REMOVAL_TIMEOUT:Long = 3000
    var itemsPendingRemoval = ArrayList<ToDoList>()

    private val handler = Handler()
    var pendingRunnables: HashMap<ToDoList, Runnable> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.task_inflater, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val toDoListAtual = toDoList[position]

        holder.nameTask.text = toDoListAtual.name



        /*if (itemsPendingRemoval.contains(toDoListAtual)) {

            holder.layoutNormal.setVisibility(View.GONE)
            holder.layoutGone.setVisibility(View.VISIBLE)
            holder.undoButton.setVisibility(View.VISIBLE)

            holder.undoButton.setOnClickListener {
                val pendingRemovalRunnable = pendingRunnables[toDoListAtual]
                pendingRunnables.remove(toDoListAtual)
                if (pendingRemovalRunnable != null) {
                    handler.removeCallbacks(pendingRemovalRunnable)
                }
                itemsPendingRemoval.remove(toDoListAtual)
                notifyItemChanged(toDoList.indexOf(toDoListAtual))
            }

        }else {

            holder.nameTask.setText(toDoListAtual.name)

            holder.layoutNormal.setVisibility(View.VISIBLE)
            holder.layoutGone.setVisibility(View.GONE)
            holder.undoButton.setVisibility(View.GONE)
            holder.undoButt


            on.setOnClickListener(null)

        }*/
    }


    fun remover (position: Int){

        var toDo= toDoList[position]

        if (toDoList.contains(toDo)){
            toDoList.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    fun removerComTempo(position: Int) {

        val toDo = toDoList[position]

        if (!itemsPendingRemoval.contains(toDo)) {

            itemsPendingRemoval.add(toDo)
            notifyItemChanged(position)

            var pendingRemovalRunnable = Runnable {
                remover(position)
            }

            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT)
            pendingRunnables[toDo] = pendingRemovalRunnable

        }

    }

    fun mover(fromPosition: Int, toPosition: Int) {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(toDoList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(toDoList, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(toPosition)
        notifyItemChanged(fromPosition)

    }

}