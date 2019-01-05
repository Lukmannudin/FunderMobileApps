package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.team.oleg.funder.Model.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*

class ChatMessageEOActivity : AppCompatActivity(), ChatMessageEOContract.View {

    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageEOContract.Presenter

    private lateinit var listAdapter: ChatMessageEOAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message_eo)
        val chatId = intent.getStringExtra(ChatUtils.CHAT_ID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listAdapter = ChatMessageEOAdapter(this, messageList)
        presenter = ChatMessageEOPresenter(chatId, this)
        rvMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter.loadChat(false)
        chatMessageSwipeRefresh.setOnRefreshListener {
            presenter.loadChat(false)
        }

        rvMessage.adapter = listAdapter
    }

    override fun setLoadingIndicator(active: Boolean) {
        chatMessageSwipeRefresh.isRefreshing = active
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.result(requestCode, resultCode)
    }

    override fun showChatList(chat: List<Message>) {
        messageList.clear()
        messageList.addAll(chat)
        listAdapter.notifyDataSetChanged()
    }

    override fun showNoChat(active: Boolean) {
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}
