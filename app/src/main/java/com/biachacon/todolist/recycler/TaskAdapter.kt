package com.biachacon.todolist.recycler

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biachacon.todolist.R
import com.biachacon.todolist.model.Task
import java.util.*

class TaskAdapter(var c: Context, var tasks:MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>() {

    private val PENDING_REMOVAL_TIMEOUT:Long = 3000
    var itemsPendingRemoval = ArrayList<Task>()

    private val handler = Handler()
    var pendingRunnables: HashMap<Task, Runnable> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.task_inflater, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val taskAtual = tasks[position]

        holder.nameTask.text = taskAtual.name


        if (taskAtual.finished){
            holder.nameTask.isChecked = true
        }

        if (itemsPendingRemoval.contains(taskAtual)) {

            holder.layoutNormal.setVisibility(View.GONE)
            holder.layoutGone.setVisibility(View.VISIBLE)
            holder.undoButton.setVisibility(View.VISIBLE)

            holder.undoButton.setOnClickListener {
                val pendingRemovalRunnable = pendingRunnables[taskAtual]
                pendingRunnables.remove(taskAtual)
                if (pendingRemovalRunnable != null) {
                    handler.removeCallbacks(pendingRemovalRunnable)
                }
                itemsPendingRemoval.remove(taskAtual)
                notifyItemChanged(tasks.indexOf(taskAtual))
            }

        }else {

            holder.nameTask.setText(taskAtual.name)

            holder.layoutNormal.setVisibility(View.VISIBLE)
            holder.layoutGone.setVisibility(View.GONE)
            holder.undoButton.setVisibility(View.GONE)
            holder.undoButton.setOnClickListener(null)

            if (taskAtual.finished){
                holder.nameTask.isChecked = true
            }
            //mudar no banco o finished
            if(holder.nameTask.isChecked){
                //holder.nameTask.isChecked = false
            }else{
                //holder.nameTask.isChecked = true
            }


        }
    }


    fun remover (position: Int){

        var task = tasks[position]

        if (tasks.contains(task)){
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    fun removerComTempo(position: Int) {

        val task = tasks[position]

        if (!itemsPendingRemoval.contains(task)) {

            itemsPendingRemoval.add(task)
            notifyItemChanged(position)

            var pendingRemovalRunnable = Runnable {
                remover(position)
            }

            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT)
            pendingRunnables[task] = pendingRemovalRunnable

        }

    }

    fun mover(fromPosition: Int, toPosition: Int) {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(tasks, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(tasks, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(toPosition)
        notifyItemChanged(fromPosition)

    }

}