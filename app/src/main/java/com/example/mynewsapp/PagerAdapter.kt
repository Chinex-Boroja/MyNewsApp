package com.example.mynewsapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class PagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    var tabCount: Int = behavior

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 ->{
                SportsFragment()
            }
            2 -> {
                EntertainmentFragment()
            }
            3 -> {
                ScienceFragment()
            }
            4 -> {
                HealthFragment()
            }
            5 -> {
                TechnologyFragment()
            }

            else -> getItem(position)
        }
    }
}