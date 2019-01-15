package com.team.oleg.funder.EventOrganizer.Chat

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Chat

interface ChatEOContract {
    interface View: BaseView<Presenter>{

        var lastMessage:String?

        fun setLoadingIndicator(active: Boolean)

        fun showChatList(chat: List<Chat>)

        fun showChatDetailUi(chatId: String)

        fun showNoChat(active: Boolean)
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadChat(forceUpdate: Boolean)

        fun openChatDetail(requestedChat: Chat)

        fun loadLastMessage(chatId:String?)

    }
}