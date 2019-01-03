package com.team.oleg.funder.Main

import MainHomeAdapter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.team.oleg.funder.BaseActivity
import com.team.oleg.funder.Chat.MainChatFragment
import com.team.oleg.funder.DealHistory.MainDealHistoryFragment
import com.team.oleg.funder.Home.HomePresenter
import com.team.oleg.funder.Home.MainHomeFragment
import com.team.oleg.funder.R
import com.team.oleg.funder.R.id.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    var prevMenuItem: MenuItem? = null
    private lateinit var homePresenter: HomePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                val MENU_HOME = 0
                val MENU_CHAT = 1
                val MENU_DEAL_HISTORY = 2

                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bottom_navigation.menu.getItem(0).isChecked = false
                }
                val bottomIconActive = bottom_navigation.menu.getItem(position)
                bottomIconActive.isChecked = true
                bottom_navigation.menu.getItem(MENU_DEAL_HISTORY).icon = resources.getDrawable(R.drawable.icon_history)

                when (position) {
                    MENU_CHAT -> {
                        bottomIconActive.icon = resources.getDrawable(R.drawable.icon_chat_active)
                    }
                    MENU_DEAL_HISTORY -> {
                        bottomIconActive.icon = resources.getDrawable(R.drawable.icon_history_active)

                    }
                    else -> {
                    }
                }
                prevMenuItem = bottomIconActive

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        setupViewPager(viewpager)
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            bnvHome -> {
                viewpager.currentItem = 0
            }
            bnvChat -> {
                viewpager.currentItem = 1
            }
            bnvDealHistory -> {
                viewpager.currentItem = 2
            }
            else -> {
                viewpager.currentItem = 0
            }
        }
        true
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MainHomeAdapter(supportFragmentManager)
        adapter.addFragment(MainHomeFragment.newInstance())
        adapter.addFragment(MainChatFragment.newInstance())
        adapter.addFragment(MainDealHistoryFragment.newInstance())
        viewPager.adapter = adapter
    }

}
