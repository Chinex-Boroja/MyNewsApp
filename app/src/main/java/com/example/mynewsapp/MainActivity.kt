package com.example.mynewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class MainActivity : AppCompatActivity() {

    var mHome: TabItem? = null
    var mSports: TabItem? = null
    var mEnt: TabItem? = null
    var mScience: TabItem? = null
    var mTech: TabItem? = null
    var mHealth: TabItem? = null
    var mToolbar: Toolbar? = null
    lateinit var pagerAdapter: PagerAdapter
    lateinit var mTableLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        mHome = findViewById(R.id.home)
        mSports = findViewById(R.id.sports)
        mEnt = findViewById(R.id.ent)
        mScience = findViewById(R.id.science)
        mTech = findViewById(R.id.tech)
        mHealth = findViewById(R.id.health)

        viewPager = findViewById<ViewPager>(R.id.fragment_container)
        mTableLayout = findViewById(R.id.include)

        pagerAdapter = PagerAdapter(supportFragmentManager, 6)
        viewPager.adapter = pagerAdapter

        //if the user directly click on the tab item
        mTableLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

                if (tab.position == 0 || tab.position == 1 || tab.position == 2 ||
                        tab.position == 3 || tab.position == 4 || tab.position == 5)
                {
                    pagerAdapter.notifyDataSetChanged()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //For swipes
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(mTableLayout))
    }
}