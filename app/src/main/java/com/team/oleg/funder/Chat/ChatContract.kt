package com.team.oleg.funder.Chat

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Model.Chat
import com.team.oleg.funder.Model.DealHistory

interface ChatContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showChatList(chat: List<Chat>)

        fun showChatDetailUi(chatId: String)

        fun showNoChat()
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadChat(forceUpdate: Boolean)

        fun openChatDetail(requestedDealHistory:Chat)
    }
}