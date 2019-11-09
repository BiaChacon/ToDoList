package com.biachacon.todolist.recycler

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector


class MyRecyclerViewClickListener(val context: Context, val view: RecyclerView, val listener:OnItemClickListener): RecyclerView.OnItemTouchListener  {

    var myGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    init {
        myGestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
                    super.onSingleTapUp(motionEvent)
                    val childView = view.findChildViewUnder(motionEvent.x, motionEvent.y)
                    if (childView != null) {
                        listener.onItemClick(childView, view.getChildAdapterPosition(childView))
                        Log.i("Teste", "onSingleTapUp ")
                    }
                    return true
                }

                override fun onLongPress(motionEvent: MotionEvent) {
                    super.onLongPress(motionEvent)
                    val childView = view.findChildViewUnder(motionEvent.x, motionEvent.y)
                    if (childView != null) {
                        listener.onItemLongClick(
                            childView,
                            view.getChildAdapterPosition(childView)
                        )
                        Log.i("Teste", "onLongPress")
                    }
                }
            })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        myGestureDetector.onTouchEvent(e);
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

}