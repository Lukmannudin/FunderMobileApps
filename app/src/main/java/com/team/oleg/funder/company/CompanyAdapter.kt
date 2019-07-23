package com.team.oleg.funder.company

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.team.oleg.funder.company.chat.ChatFragment
import com.team.oleg.funder.company.Request.RequestFragment

class CompanyAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    private val CHAT_VIEW = 0
    private val REQUEST_VIEW = 0

    private val pages: List<androidx.fragment.app.Fragment> = listOf(
        ChatFragment(),
        RequestFragment()
    )

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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