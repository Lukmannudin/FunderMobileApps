package com.team.oleg.funder.Company

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.team.oleg.funder.Company.Chat.ChatFragment
import com.team.oleg.funder.Company.Request.RequestFragment

class CompanyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val CHAT_VIEW = 0
    private val REQUEST_VIEW = 0

    private val pages: List<Fragment> = listOf(
        ChatFragment(),
        RequestFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            CHAT_VIEW -> "Chats"
            else -> {
                "Request"
            }
        }
    }

}