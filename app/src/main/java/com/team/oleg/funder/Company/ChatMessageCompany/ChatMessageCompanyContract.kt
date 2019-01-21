package com.team.oleg.funder.Company.ChatMessageCompany

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Message

interface ChatMessageCompanyContract {
    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showChatList(chat: List<Message>)

        fun showNewChat(chat: Message)

        fun showNoChat(active: Boolean)

        fun setMessage(textMessage:String,status:Boolean)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadChat(forceUpdate: Boolean)

        fun sendChat(message: Message)

        fun realAllMessage(chatId:String?)

        fun cekOnline(message:String, chatId: String?)

        fun setOnline(chatId: String?,status:Boolean )

    }
}