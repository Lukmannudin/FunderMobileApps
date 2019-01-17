package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.Database.database
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import org.jetbrains.anko.db.insert

class ChatMessageEOActivity : AppCompatActivity(), ChatMessageEOContract.View {


    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageEOContract.Presenter

    private lateinit var listAdapter: ChatMessageEOAdapter

    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message_eo)
        chatId = intent.getStringExtra(ChatUtils.CHAT_ID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listAdapter = ChatMessageEOAdapter(this, messageList)
        presenter = ChatMessageEOPresenter(this, chatId, this)
        rvMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter.loadChat(false)

        rvMessage.adapter = listAdapter
        realtimeUpdateListener()
        ivSendMessage.setOnClickListener {
            setMessage()
            edtAddMessage.text.clear()
        }


    }

    override fun setLoadingIndicator(active: Boolean) {
//        chatMessageSwipeRefresh.isRefreshing = active
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
        rvMessage.smoothScrollToPosition(messageList.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.result(requestCode, resultCode)
    }

    override fun showChatList(chat: List<Message>) {
        messageList.clear()
        messageList.addAll(chat)
        listAdapter.notifyDataSetChanged()
        rvMessage.smoothScrollToPosition(messageList.size)
    }

    override fun showNoChat(active: Boolean) {
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
        readMessage()

    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(ChatUtils.COLLECTION_KEY).document(ChatUtils.DOCUMENT_KEY)
    }

    private fun readMessage(){
        val map = HashMap<String,Any?>()
        map[ChatUtils.MESSAGE_STATUS_SENDING] = 200
        map[ChatUtils.CHAT_ID] = chatId

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
        message.sender = Utils.SENDER_EO
        message.message = edtAddMessage.text.toString()
        message.messageStatus = "sent"

        Log.i("message setMessage", message.message.toString())
        showNewChat(message)
        presenter.sendChat(message)
        firestoreChat.set(message)
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
                        if (chatId == data?.get(ChatUtils.CHAT_ID) && Utils.SENDER_COMPANY == data?.get(ChatUtils.SENDER)) {
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
                        } else if(data?.get(ChatUtils.MESSAGE_STATUS_SENDING) != null){
                            if (data?.get(ChatUtils.MESSAGE_STATUS_SENDING) == 200
                                && data?.get(ChatUtils.CHAT_ID) == chatId
                            ){
                                for (i in 0 until messageList.size){
                                    if (messageList[i].sender == Utils.SENDER_EO){
                                        messageList[i].messageRead = "1"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel(messages: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = messages
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun addToDatabase(message: Map<String, String?>) {
        try {
            database.use {
                insert(
                    ChatUtils.TABLE_CHAT,
                    ChatUtils.MESSAGE_ID to message.get(ChatUtils.MESSAGE_ID),
                    ChatUtils.CHAT_ID to message.get(ChatUtils.CHAT_ID),
                    ChatUtils.SENDER to message.get(ChatUtils.SENDER),
                    ChatUtils.MESSAGE to message.get(ChatUtils.MESSAGE),
                    ChatUtils.MESSAGE_TIME to message.get(ChatUtils.MESSAGE_TIME),
                    ChatUtils.MESSAGE_STATUS to message.get(ChatUtils.MESSAGE_STATUS),
                    ChatUtils.MESSAGE_READ to message.get(ChatUtils.MESSAGE_READ)
                )
            }
        } catch (e: SQLiteConstraintException) {
            Log.i("coco", e.localizedMessage)
        }
    }


}
