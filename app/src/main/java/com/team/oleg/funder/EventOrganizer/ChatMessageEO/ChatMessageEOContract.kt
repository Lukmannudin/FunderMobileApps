package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Message

interface ChatMessageEOContract {
    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showChatList(chat: List<Message>)

        fun showNoChat(active: Boolean)

        fun showNewChat(chat: Message)
    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadChat(forceUpdate: Boolean)

        fun sendChat(message: Message)
    }
}