package com.team.oleg.funder.Company.ChatMessageCompany

import android.util.Log
import com.team.oleg.funder.APIRequest.ChatService
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChatMessageCompanyPresenter(
    private val chatId: String?,
    private val chatEOView: ChatMessageCompanyContract.View
) : ChatMessageCompanyContract.Presenter {

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

        val service: ChatService = ApiService.chatService
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
        val service: ChatService = ApiService.chatService
        disposable = service.sendMessage(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            chatEOView.showNewChat(message)
                        },
                        { error ->
                            Log.i("cek","GAGAL CHAT")
                            Log.i("cek g",error.localizedMessage)
                            Log.i("cek c",error.message)
                            Log.i("cek k",error.stackTrace[0].className)


//                            chatEOView.showNoChat(true)
                        }
                )
    }

}