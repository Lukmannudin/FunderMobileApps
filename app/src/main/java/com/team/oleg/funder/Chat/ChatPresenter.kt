package com.team.oleg.funder.Chat

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestApiChat
import com.team.oleg.funder.Model.Chat
import com.team.oleg.funder.Service.ChatService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatPresenter(
    private val chatView: ChatContract.View
) : ChatContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        chatView.presenter = this
    }


    override fun start() {
        loadChat(false)
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

        val service: RequestApiChat = ChatService.create()
        disposable = service.getChatEO()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("cekl",result.data[0].companyName)
//                    processChat(result.data)
//                    chatView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("ErrorLOL", error.message)
                }
            )
    }

    private fun processChat(chat: List<Chat>) {
        if (chat.isEmpty()) {
            Log.i("cek", "isEmpty")
        } else {
            chatView.showChatList(chat)
        }
    }

    override fun openChatDetail(requestChat: Chat) {
        requestChat.chatId?.let {
            chatView.showChatDetailUi(it)
        }
    }


}