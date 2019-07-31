package com.team.oleg.funder.company.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.team.oleg.funder.data.Chat
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.SharedPreferenceUtils
import com.team.oleg.funder.utils.Utils
import com.team.oleg.funder.company.chatMessageCompany.ChatMessageCompanyActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import org.jetbrains.anko.support.v4.intentFor

class ChatFragment : androidx.fragment.app.Fragment(), ChatCompanyContract.View {


    override lateinit var presenter: ChatCompanyContract.Presenter

    private val chatList: MutableList<Chat> = mutableListOf()
    val temp: MutableList<Chat> = mutableListOf()
    private lateinit var searchView: SearchView

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
        view.rvChatCompany.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        view.chatCompanySwipeRefresh.setOnRefreshListener {
            presenter.loadChat(false)
        }
        setHasOptionsMenu(true)
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
        temp.addAll(chat)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.toolbar_company, menu)
        searchView = menu?.findItem(R.id.searchCompany)?.actionView as SearchView
        temp.addAll(chatList)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                if (chatList.isEmpty() || chatList.size == 0) {
                    chatList.addAll(temp)
                }
                if (!chatList.isEmpty() && chatList.size > 0) {
                    val dump: MutableList<Chat> = mutableListOf()
                    for (i in chatList.indices) {
                        searchString?.let {
                            if (chatList[i].eoName?.contains(searchString.toRegex())!!) {
                                dump.add(chatList[i])
                            }
                        }
                    }
                    chatList.clear()
                    chatList.addAll(dump)
                    listAdapter.notifyDataSetChanged()
                }
                return true
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                chatList.clear()
                chatList.addAll(temp)
                listAdapter.notifyDataSetChanged()
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
        searchView.isIconified = true
    }

    fun logout() {
        val sharedPref = activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0)
        if (sharedPref?.getString(
                SharedPreferenceUtils.USER_ID,
                SharedPreferenceUtils.EMPTY
            ) != SharedPreferenceUtils.EMPTY
        ) {
            sharedPref?.edit()?.clear()?.apply()
            activity?.startActivity(intentFor<LoginEOActivity>())
        }
    }

    interface ChatItemListener {
        fun onChatClick(clicked: Chat)
    }

}
