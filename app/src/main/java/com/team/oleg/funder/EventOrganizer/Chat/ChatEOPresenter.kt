package com.team.oleg.funder.EventOrganizer.Chat

import com.team.oleg.funder.APIRequest.ChatService
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatEOPresenter(
    private val userId: String?,
    private val chatEOView: ChatEOContract.View
) : ChatEOContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        chatEOView.presenter = this
    }


    override fun start() {
        loadChat(false)
        chatEOView.showNoChat(false)
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
            chatEOView.setLoadingIndicator(true)
        }

//        if (forceUpdate) {
//        }

        val service: ChatService = ApiService.chatService
        disposable = service.getChatEO(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processChat(result.data)
                    chatEOView.setLoadingIndicator(false)
                },
                { error ->
                    chatEOView.showNoChat(true)
                }
            )
    }

    private fun processChat(chat: List<Chat>) {
        if (chat.isEmpty()) {
            chatEOView.showNoChat(true)
        } else {
            chatEOView.showChatList(chat)
        }
    }

    override fun openChatDetail(requestedChat: Chat) {
        requestedChat.chatId?.let {
            chatEOView.showChatDetailUi(it)
        }
    }


}