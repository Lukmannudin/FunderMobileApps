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
    private val chatCompanyView: ChatMessageCompanyContract.View
) : ChatMessageCompanyContract.Presenter {
   

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        chatCompanyView.presenter = this
    }


    override fun start() {
        loadChat(false)
        Log.i("cek FirstLoad",firstLoad.toString())

//        chatCompanyView.showNoChat(false)
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
            chatCompanyView.setLoadingIndicator(true)
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
                    chatCompanyView.setLoadingIndicator(false)
                },
                { error ->
                    chatCompanyView.showNoChat(true)
                }
            )
    }

    private fun processChat(chat: List<Message>) {
        if (chat.isEmpty()) {
            chatCompanyView.showNoChat(true)
        } else {
            chatCompanyView.showChatList(chat)
        }
    }

    override fun sendChat(message: Message) {
        val service: ChatService = ApiService.chatService
        disposable = service.sendMessage(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->

                },
                { error ->
                    println(error.localizedMessage)
                }
            )
    }

    override fun receiveChat(message: Message) {
        chatCompanyView.showNewChat(message)
    }

}