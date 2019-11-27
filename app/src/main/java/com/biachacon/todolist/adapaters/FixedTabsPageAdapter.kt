package com.biachacon.todolist.adapaters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.biachacon.todolist.fragments.Fragment1
import com.biachacon.todolist.fragments.*


class FixedTabsPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val f1 = Fragment1()
    val f2 = Fragment2()
    val f3 = Fragment3()


    override fun getItem(position: Int): Fragment? {
        return when(position){
            0-> f1
            1-> f2
            2-> f3
            else-> null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"All Tasks"
            1->"List"
            2->"Finished"
            else-> null
        }
    }
}