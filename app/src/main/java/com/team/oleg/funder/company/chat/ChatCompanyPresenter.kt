package com.team.oleg.funder.company.chat

import com.team.oleg.funder.apirequest.ChatService
import com.team.oleg.funder.data.Chat
import com.team.oleg.funder.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatCompanyPresenter(
    private val userId: String?,
    private val chatView: ChatCompanyContract.View
) : ChatCompanyContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        chatView.presenter = this
    }


    override fun start() {
        loadChat(false)
        chatView.showNoChat(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }


    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadChat(forceUpdate: Boolean) {
        loadChat(forceUpdate || firstLoad, true)
    }

    private fun loadChat(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            chatView.setLoadingIndicator(true)
        }

//        if (forceUpdate) {
//        }

        val service: ChatService = ApiService.chatService
        disposable = service.getChatCompany(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processChat(result.data)
                    chatView.setLoadingIndicator(false)
                },
                { error ->
                    chatView.showNoChat(true)
                }
            )
    }

    private fun processChat(chat: List<Chat>) {
        if (chat.isEmpty()) {
            chatView.showNoChat(true)
        } else {
            chatView.showChatList(chat)
        }
    }

    override fun openChatDetail(requestedChat: Chat) {
        chatView.showChatDetailUi(requestedChat)
//        chatView.showChatDetailUi(requestedChat.chatId!!,requestedChat.companyName!!)
    }


}