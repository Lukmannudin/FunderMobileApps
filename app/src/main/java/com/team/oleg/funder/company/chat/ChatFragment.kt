package com.team.oleg.funder.company.chat

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.company.chatMessageCompany.ChatMessageCompanyActivity
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import org.jetbrains.anko.support.v4.intentFor

class ChatFragment : Fragment(), ChatCompanyContract.View {


    override lateinit var presenter: ChatCompanyContract.Presenter

    private val chatList: MutableList<Chat> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = ChatCompanyPresenter(userId, this)
    }

    private val itemListener: ChatFragment.ChatItemListener = object :
        ChatFragment.ChatItemListener {
        override fun onChatClick(clicked: Chat) {
            presenter.openChatDetail(clicked)
        }
    }

    private lateinit var listAdapter: ChatCompanyAdapter

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = ChatCompanyAdapter(context, chatList, itemListener)
        rvChatCompany.adapter = listAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        view.rvChatCompany.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.chatCompanySwipeRefresh.setOnRefreshListener {
            presenter.loadChat(false)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        chatCompanySwipeRefresh.isRefreshing = false
    }

    override fun showChatList(chat: List<Chat>) {
        chatList.clear()
        chatList.addAll(chat)
        listAdapter.notifyDataSetChanged()
    }


    override fun showChatDetailUi(chat: Chat) {
        startActivity(
            intentFor<ChatMessageCompanyActivity>(
                Utils.INTENT_PARCELABLE to chat
            )
        )
    }

    override fun showNoChat(active: Boolean) {
    }

    interface ChatItemListener {
        fun onChatClick(clicked: Chat)
    }

}
