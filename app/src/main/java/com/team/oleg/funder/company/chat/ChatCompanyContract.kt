package com.team.oleg.funder.company.chat

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.data.Chat

interface ChatCompanyContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showChatList(chat: List<Chat>)

        fun showChatDetailUi(chat: Chat)

        fun showNoChat(active: Boolean)
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadChat(forceUpdate: Boolean)

        fun openChatDetail(requestedChat: Chat)

    }
}