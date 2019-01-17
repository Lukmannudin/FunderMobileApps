package com.team.oleg.funder.Company.ChatMessageCompany

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.ChatUtils.CHAT_ID
import com.team.oleg.funder.Utils.ChatUtils.COLLECTION_KEY
import com.team.oleg.funder.Utils.ChatUtils.DOCUMENT_KEY
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*

class ChatMessageCompanyActivity : AppCompatActivity(), ChatMessageCompanyContract.View {


    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageCompanyContract.Presenter

    private lateinit var listAdapter: ChatMessageCompanyAdapter
    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message_eo)
        chatId = intent.getStringExtra(ChatUtils.CHAT_ID)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listAdapter = ChatMessageCompanyAdapter(this, messageList)
        presenter = ChatMessageCompanyPresenter(chatId, this)
        rvMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter.loadChat(false)
//        chatMessageSwipeRefresh.setOnRefreshListener {
//            presenter.loadChat(false)
//        }

        rvMessage.adapter = listAdapter
        realtimeUpdateListener()
        ivSendMessage.setOnClickListener {
            setMessage()
            edtAddMessage.text.clear()
        }
        readMessage()
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun onStart() {
        super.onStart()
        presenter.start()

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

    override fun showNewChat(chat: Message) {
        Log.i("cek", "showNewChat")
        messageList.add(chat)
        listAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }

    private fun readMessage(){
        val map = HashMap<Int,Any?>()
        map[ChatUtils.MESSAGE_READ_SUCCESS_CODE] = 200
//        map[ChatUtils.MESSAGE_STATUS_SENDING] = chatId
//        map[ChatUtils.MESSAGE_READ] = true

        firestoreChat.set(map)
            .addOnSuccessListener {
//                Toast.makeText(this@ChatMessageCompanyActivity, "Message Sent", Toast.LENGTH_SHORT).show()
//                presenter.sendChat(message)
            }
            .addOnFailureListener { e ->
//                Log.i("cek Error", e.message)
            }
    }

    private fun setMessage() {

        val message = Message()
        message.chatId = chatId
        message.sender = Utils.SENDER_COMPANY
        message.message = edtAddMessage.text.toString()
        message.messageStatus = ChatUtils.MESSAGE_STATUS_SENT

        showNewChat(message)
        firestoreChat.set(message)
            .addOnSuccessListener {
                Toast.makeText(this@ChatMessageCompanyActivity, "Message Sent", Toast.LENGTH_SHORT).show()
                presenter.sendChat(message)
            }
            .addOnFailureListener { e ->
                Log.i("cek Error", e.message)
            }
    }

    private fun realtimeUpdateListener() {
        firestoreChat.addSnapshotListener { documentSnapshot,
                                            firebaseFirestoreException ->
            when {
                firebaseFirestoreException != null -> Log.i("ERROR", firebaseFirestoreException.message)
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        if (chatId == data?.get(CHAT_ID) && Utils.SENDER_EO == data?.get(ChatUtils.SENDER)) {
                            showNewChat(
                                Message(
                                    null,
                                    data?.get(ChatUtils.CHAT_ID).toString(),
                                    data?.get(ChatUtils.SENDER).toString(),
                                    data?.get(ChatUtils.MESSAGE).toString(),
                                    null,
                                    data?.get(ChatUtils.MESSAGE_STATUS).toString(),
                                    null
                                )
                            )
                        }else if(data?.get("messageForRead") != null){
                            if (data?.get("messageForRead") == 200){

                            }
                        }
                    }
                }
            }

        }
    }
}
