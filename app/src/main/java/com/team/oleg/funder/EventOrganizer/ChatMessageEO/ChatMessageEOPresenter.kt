package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import com.team.oleg.funder.APIRequest.RequestApiChat
import com.team.oleg.funder.Model.Message
import com.team.oleg.funder.Service.ChatService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatMessageEOPresenter(
    private val chatId: String?,
    private val chatEOView: ChatMessageEOContract.View
) : ChatMessageEOContract.Presenter {


    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        chatEOView.presenter = this
    }


    override fun start() {
        loadChat(false)
//        chatEOView.showNoChat(false)
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

        val service: RequestApiChat = ChatService.create()
        disposable = service.getMessageEO(chatId)
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

    private fun processChat(chat: List<Message>) {
        if (chat.isEmpty()) {
            chatEOView.showNoChat(true)
        } else {
            chatEOView.showChatList(chat)
        }
    }

    override fun sendChat(message: Message) {
        chatEOView.showNewChat(message)
    }

}