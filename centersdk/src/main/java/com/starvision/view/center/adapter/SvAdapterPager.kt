package com.starvision.view.center.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SvAdapterPager(fm:FragmentActivity, val fragments:ArrayList<Fragment>):FragmentStateAdapter(fm) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}