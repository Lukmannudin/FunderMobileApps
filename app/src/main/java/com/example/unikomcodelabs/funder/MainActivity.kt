package com.example.unikomcodelabs.funder

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.unikomcodelabs.funder.MainFragment.MainChatFragment
import com.example.unikomcodelabs.funder.MainFragment.MainDealHistoryFragment
import com.example.unikomcodelabs.funder.MainFragment.MainHomeFragment
import com.example.unikomcodelabs.funder.R.id.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity<LoginActivity>()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            bnvHome -> {
                val homeFragment = MainHomeFragment.newInstance()
                openFragment(homeFragment)
            }
            bnvChat -> {
                val homeFragment = MainChatFragment.newInstance()
                openFragment(homeFragment)
            }
            bnvDealHistory -> {
                val homeFragment = MainDealHistoryFragment.newInstance()
                openFragment(homeFragment)
            }
        }
        bottom_navigation.selectedItemId = bnvHome
        true
    }

    private fun openFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
