package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import android.content.Context
import android.util.Log
import com.team.oleg.funder.APIRequest.ChatService
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.Database.database
import com.team.oleg.funder.Service.ApiService
import com.team.oleg.funder.Utils.ChatUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class ChatMessageEOPresenter(
    private val context: Context,
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
        firstLoad = false
    }

    private fun loadChat(forceUpdate: Boolean, showLoadingUI: Boolean) {

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
                    var cau: String? = null
                    context.database.use {
                        val resulte = select(ChatUtils.TABLE_CHAT)
                        val message = resulte.parseList(classParser<Message>())
                        cau = message[0].message
                    }
                    Log.i("coco", cau)

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
                    Log.i("coconot", "BERHASIL CHAT")
//                            chatEOView.showNewChat(message)
                },
                { error ->
                    //                            chatEOView.showNoChat(true)
                }
            )
    }

    override fun realAllMessage(chatId: String?) {
        val service: ChatService = ApiService.chatService
        disposable = service.readAllMessage(chatId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("berhasil", "berhasilread")
                },
                { error ->
                    println(error.localizedMessage)
                    Log.i("berhasil", error.localizedMessage)
                }
            )
    }

    override fun cekOnline(message: String, chatId: String?) {
        val service: ChatService = ApiService.chatService

        disposable = service.cekOnlineCompany(chatId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    chatEOView.setMessage(message,result.data)
                },
                { error ->
                    println(error.localizedMessage)
                    Log.i("berhasil", error.localizedMessage)
                }
            )
    }


    override fun setOnline(chatId: String?, status: Boolean) {
        val service: ChatService = ApiService.chatService
        var statusOnline = service.setOnlineEO(chatId)

        if (!status){
            statusOnline = service.setOfflineEO(chatId)
        }

        disposable = statusOnline
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("berhasil", "berhasilread")
                },
                { error ->
                    println(error.localizedMessage)
                    Log.i("berhasil", error.localizedMessage)
                }
            )
    }


}