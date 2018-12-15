package com.team.oleg.funder

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.Chat.MainChatFragment
import com.team.oleg.funder.DealHistory.MainDealHistoryFragment
import com.team.oleg.funder.Home.MainHomeFragment
import com.team.oleg.funder.R.id.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity<LoginActivity>()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportActionBar?.hide()
        initView()
    }


    private fun initView(){
        val homeFragment = MainHomeFragment.newInstance()
        openFragment(homeFragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val homeFragment = MainHomeFragment.newInstance()
        val chatFragment = MainChatFragment.newInstance()
        val dealHistoryFragment = MainDealHistoryFragment.newInstance()
        when (item.itemId) {
            bnvHome -> {
                openFragment(homeFragment)
            }
            bnvChat -> {
                openFragment(chatFragment)
            }
            bnvDealHistory -> {
                openFragment(dealHistoryFragment)
            }
            else -> {
                openFragment(homeFragment)
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

}
