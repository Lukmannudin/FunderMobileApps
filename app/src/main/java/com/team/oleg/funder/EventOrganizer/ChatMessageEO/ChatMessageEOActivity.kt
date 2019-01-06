package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.team.oleg.funder.Model.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import java.util.*

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


        ivSendMessage.setOnClickListener {
            setMessage()
            edtAddMessage.text.clear()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        chatMessageSwipeRefresh.isRefreshing = active
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }
    override fun showNewChat(chat: Message) {
        messageList.add(chat)
        listAdapter.notifyDataSetChanged()
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
        realtimeUpdateListener()
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(ChatUtils.COLLECTION_KEY).document(ChatUtils.DOCUMENT_KEY)
    }

    private fun setMessage() {

        val newMesage = mapOf(
            ChatUtils.MESSAGE_ID to null,
            ChatUtils.CHAT_ID to messageList[0].chatId,
            ChatUtils.SENDER to Utils.SENDER_COMPANY,
            ChatUtils.MESSAGE to edtAddMessage.text.toString(),
            ChatUtils.MESSAGE_TIME to Date().toString(),
            ChatUtils.MESSAGE_STATUS to "sent",
            ChatUtils.MESSAGE_READ to "0"
        )

        firestoreChat.set(newMesage)
            .addOnSuccessListener {
                Toast.makeText(this@ChatMessageEOActivity, "Message Sent", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.i("ERROR", e.message)
            }
    }


    private fun realtimeUpdateListener() {
        firestoreChat.addSnapshotListener { documentSnapshot,
                                            firebaseFirestoreException ->
            when {
                firebaseFirestoreException != null -> Log.i("ERROR", firebaseFirestoreException.message)
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        //                        displayMessage.text = "${data?.get(NAME_FIELD)}:${data?.get(TEXT_FIELD)}"
//                        if (data != null){
//                            Toast.makeText(this@ChatMessageEOActivity,"${data?.get(ChatUtils.MESSAGE)}", Toast.LENGTH_SHORT).show()
//                        }
                        if (data?.get(ChatUtils.CHAT_ID) != null) {
//                            if (data?.get(ChatUtils.CHAT_ID)) {
                                presenter.sendChat(
                                    com.team.oleg.funder.Model.Message(
                                        null,
                                        data?.get(ChatUtils.CHAT_ID).toString(),
                                        data?.get(ChatUtils.SENDER).toString(),
                                        data?.get(ChatUtils.MESSAGE).toString(),
                                        data?.get(ChatUtils.MESSAGE_TIME).toString(),
                                        data?.get(ChatUtils.MESSAGE_STATUS).toString(),
                                        data?.get(ChatUtils.MESSAGE_READ).toString()
                                    )
                                )
//                            }
                        }
                    }
                }
            }

        }
    }
}
