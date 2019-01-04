package com.team.oleg.funder.Company.Chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Model.Chat
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_main_chat.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatFragment : Fragment(), ChatCompanyContract.View {

    override lateinit var presenter: ChatCompanyContract.Presenter

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val chatList: MutableList<Chat> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = ChatCompanyPresenter(userId, this)
    }

    private val itemListener: ChatFragment.chatItemListener = object :
        ChatFragment.chatItemListener {
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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun setLoadingIndicator(active: Boolean) {
        chatCompanySwipeRefresh.isRefreshing = false
    }

    override fun showChatList(chat: List<Chat>) {
        chatList.clear()
        chatList.addAll(chat)
        listAdapter.notifyDataSetChanged()
    }

    override fun showChatDetailUi(chatId: String) {
    }

    override fun showNoChat(active: Boolean) {
    }

    interface chatItemListener {
        fun onChatClick(clicked: Chat)
    }
}
