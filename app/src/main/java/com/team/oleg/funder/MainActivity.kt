package com.team.oleg.funder

import ViewPagerAdapter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.team.oleg.funder.Chat.MainChatFragment
import com.team.oleg.funder.DealHistory.MainDealHistoryFragment
import com.team.oleg.funder.Home.MainHomeFragment
import com.team.oleg.funder.R.id.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var prevMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity<LoginActivity>()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        initView()
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bottom_navigation.menu.getItem(0).isChecked = false
                }
                Log.d("page", "onPageSelected: $position")
                bottom_navigation.menu.getItem(position).isChecked = true
                prevMenuItem = bottom_navigation.menu.getItem(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        setupViewPager(viewpager)
    }
//    private fun initView(){
//        val homeFragment = MainHomeFragment.newInstance()
//        openFragment(homeFragment)
//    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val homeFragment = MainHomeFragment.newInstance()
        val chatFragment = MainChatFragment.newInstance()
        val dealHistoryFragment = MainDealHistoryFragment.newInstance()
        when (item.itemId) {
            bnvHome -> {
//                openFragment(homeFragment)
                viewpager.currentItem = 0
            }
            bnvChat -> {
//                openFragment(chatFragment)
            viewpager.currentItem = 1
            }
            bnvDealHistory -> {
//                openFragment(dealHistoryFragment)
                viewpager.currentItem = 2
            }
            else -> {
//                openFragment(homeFragment)
                viewpager.currentItem = 0
            }
        }
        true
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainHomeFragment.newInstance())
        adapter.addFragment(MainChatFragment.newInstance())
        adapter.addFragment(MainDealHistoryFragment.newInstance())
        viewPager.adapter = adapter
    }

}
