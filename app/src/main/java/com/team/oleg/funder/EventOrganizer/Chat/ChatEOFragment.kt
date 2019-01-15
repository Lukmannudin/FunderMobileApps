package com.team.oleg.funder.EventOrganizer.Chat

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.EventOrganizer.ChatMessageEO.ChatMessageEOActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.fragment_main_chat.*
import kotlinx.android.synthetic.main.fragment_main_chat.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.intentFor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainChatFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainChatFragment : Fragment(), ChatEOContract.View {
    private var chaS: Int = 0
    override fun showChat(chat: Chat, chatSize: Int) {
        chaS += 1
        Log.i("cha", chaS.toString())
        if (chaS >= chatSize) {
            chatListB.clear()
            chaS = 0
        } else if (chaS<chatSize) {
            chatListB.add(chat)
            Log.i("BABI", "BABI:${chatSize}:${chat.message}:${chat.companyName}:${chatListB.size}")
            if (chatListB.size == chatSize - 1) {
                chatListB.add(chatList[chatSize - 1])
                listEOAdapter.notifyDataSetChanged()
            }
        }

    }

    override var lastMessage: String? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override lateinit var presenter: ChatEOContract.Presenter
    private val chatList: MutableList<Chat> = mutableListOf()
    private val chatListB: MutableList<Chat> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = ChatEOPresenter(userId, this)
    }

    private val itemListener: chatItemListener = object :
        chatItemListener {
        override fun getLastMessage(chatId: String?): String? {
//            presenter.loadLastMessage(chatId)
            listEOAdapter.notifyDataSetChanged()
            return this@MainChatFragment.lastMessage
        }

        override fun onChatClick(clicked: Chat) {
            presenter.openChatDetail(clicked)
        }
    }

    private lateinit var listEOAdapter: ChatEOAdapter

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listEOAdapter = ChatEOAdapter(context, chatListB, itemListener)
        rvMainChat.adapter = listEOAdapter
        setupSearchView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        val view = inflater.inflate(R.layout.fragment_main_chat, container, false)
        view.rvMainChat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.chatSwipeRefresh.setOnRefreshListener {
//            presenter.loadChat(false)
            chatSwipeRefresh.isRefreshing = false
        }
        view.no_main_chat.visibility = View.VISIBLE
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainChatFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MainChatFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
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

    override fun showChatDetailUi(chatId: String) {
        startActivity(
            intentFor<ChatMessageEOActivity>(
                ChatUtils.CHAT_ID to chatId
            )
        )
    }

    override fun showNoChat(active: Boolean) {
        if (active) {
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
        val searchIconImage = actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_button)
        searchIconImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.icon_search) })
        tbTitle.text = getString(R.string.title_chat)
        val searchIconCloseImage =
            actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
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

        val searchIconEditText = actionSearch.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchIconEditText.setTextColor(resources.getColor(R.color.white))

        actionSearch.setOnCloseListener {
            actionSearch.layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT
            tbTitle.visibility = View.VISIBLE
            false
        }
    }

    interface chatItemListener {
        fun onChatClick(clicked: Chat)
        fun getLastMessage(chatId: String?): String?
    }

}
