package com.team.oleg.funder.Company.ChatMessageCompany

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
import com.team.oleg.funder.Utils.ChatUtils.CHAT_ID
import com.team.oleg.funder.Utils.ChatUtils.COLLECTION_KEY
import com.team.oleg.funder.Utils.ChatUtils.DOCUMENT_KEY
import com.team.oleg.funder.Utils.ChatUtils.MESSAGE
import com.team.oleg.funder.Utils.ChatUtils.MESSAGE_ID
import com.team.oleg.funder.Utils.ChatUtils.MESSAGE_READ
import com.team.oleg.funder.Utils.ChatUtils.MESSAGE_STATUS
import com.team.oleg.funder.Utils.ChatUtils.MESSAGE_TIME
import com.team.oleg.funder.Utils.ChatUtils.SENDER
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import java.util.*

class ChatMessageCompanyActivity : AppCompatActivity(), ChatMessageCompanyContract.View {


    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageCompanyContract.Presenter

    private lateinit var listAdapter: ChatMessageCompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message_eo)
        val chatId = intent.getStringExtra(ChatUtils.CHAT_ID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listAdapter = ChatMessageCompanyAdapter(this, messageList)
        presenter = ChatMessageCompanyPresenter(chatId, this)
        rvMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter.loadChat(false)
        chatMessageSwipeRefresh.setOnRefreshListener {
            presenter.loadChat(false)
        }

        rvMessage.adapter = listAdapter
        realtimeUpdateListener()
        ivSendMessage.setOnClickListener {
            presenter.sendChat(
                Message(
                    null,
                    messageList[0].chatId,
                    Utils.SENDER_EO,
                    edtAddMessage.text.toString(),
                    Date().toString(),
                    "sent",
                    "0"
                )
            )
            sendMessage()
            edtAddMessage.text.clear()
        }


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

    override fun showNewChat(chat: Message) {
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

    private fun sendMessage(){

        val newMesage = mapOf(
            MESSAGE_ID to null,
            CHAT_ID to messageList[0].chatId,
            SENDER to Utils.SENDER_EO,
            MESSAGE to edtAddMessage.text.toString(),
            MESSAGE_TIME to Date().toString(),
            MESSAGE_STATUS to "sent",
            MESSAGE_READ to "0"
            )

        firestoreChat.set(newMesage)
            .addOnSuccessListener {
                Toast.makeText(this@ChatMessageCompanyActivity,"Message Sent",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                e -> Log.i("ERROR",e.message)
            }
    }

    private fun realtimeUpdateListener(){
        firestoreChat.addSnapshotListener {
                documentSnapshot,
                firebaseFirestoreException ->
            when {
                firebaseFirestoreException != null -> Log.i("ERROR", firebaseFirestoreException.message)
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot){
//                        displayMessage.text = "${data?.get(NAME_FIELD)}:${data?.get(TEXT_FIELD)}"
                        if (data?.get(CHAT_ID) == messageList[0].chatId){
                            presenter.sendChat(
                                Message(
                                    null,
                                    messageList[0].chatId,
                                    data?.get(SENDER).toString(),
                                    data?.get(MESSAGE).toString(),
                                    data?.get(MESSAGE_TIME).toString(),
                                    data?.get(MESSAGE_STATUS).toString(),
                                    data?.get(MESSAGE_READ).toString()
                                )
                            )
                        }
                    }
                }
            }

        }
    }
}
