package com.team.oleg.funder.EventOrganizer.Chat

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.team.oleg.funder.EventOrganizer.ChatMessageEO.ChatMessageEOActivity
import com.team.oleg.funder.data.Chat
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.SharedPreferenceUtils
import com.team.oleg.funder.utils.Utils
import kotlinx.android.synthetic.main.fragment_main_chat.*
import kotlinx.android.synthetic.main.fragment_main_chat.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.intentFor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainChatFragment : androidx.fragment.app.Fragment(), ChatEOContract.View {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override lateinit var presenter: ChatEOContract.Presenter
    private val chatList: MutableList<Chat> = mutableListOf()
    private lateinit var listEOAdapter: ChatEOAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = ChatEOPresenter(userId,this)
    }

    private val itemListener: chatItemListener = object :
        chatItemListener {
        override fun onChatClick(clicked: Chat) {
            presenter.openChatDetail(clicked)
        }
    }



    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listEOAdapter = ChatEOAdapter(context, chatList, itemListener)
        rvMainChat.adapter = listEOAdapter
        setupSearchView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        val view =  inflater.inflate(R.layout.fragment_main_chat, container, false)
        view.rvMainChat.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        view.chatSwipeRefresh.setOnRefreshListener {
            presenter.loadChat(false)
        }
        view.no_main_chat.visibility = View.VISIBLE
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainChatFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        chatSwipeRefresh.isRefreshing = active
    }

    override fun showChatList(chat: List<Chat>) {
        chatList.clear()
        chatList.addAll(chat)
        listEOAdapter.notifyDataSetChanged()
    }

    override fun showChatDetailUi(chat: Chat) {
        startActivity(intentFor<ChatMessageEOActivity>(
            Utils.INTENT_PARCELABLE to chat
        ))
    }

    override fun showNoChat(active: Boolean) {
        if (active){
            no_main_chat.visibility = View.VISIBLE
            chatSwipeRefresh.visibility = View.GONE
        } else {
            no_main_chat.visibility = View.GONE
            chatSwipeRefresh.visibility = View.VISIBLE
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    private fun setupSearchView() {
        val searchIconImage = actionSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        searchIconImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.icon_search) })
        tbTitle.text = getString(R.string.title_chat)
        val searchIconCloseImage =
            actionSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        searchIconCloseImage.setImageDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.ic_close_white_24dp
            )
        })
        actionSearch.setOnSearchClickListener {
            tbTitle.visibility = View.GONE
            actionSearch.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
        }

        val searchIconEditText = actionSearch.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchIconEditText.setTextColor(resources.getColor(R.color.white))

        actionSearch.setOnCloseListener {
            actionSearch.layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT
            tbTitle.visibility = View.VISIBLE
            false
        }
    }

    interface chatItemListener {
        fun onChatClick(clicked: Chat)
    }

}
