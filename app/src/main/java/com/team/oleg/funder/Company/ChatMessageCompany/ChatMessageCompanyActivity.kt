package com.team.oleg.funder.Company.ChatMessageCompany

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Company.DealForm.DealFormActivity
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.EOProfile.EoProfileActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.ChatUtils.CHAT_ID
import com.team.oleg.funder.Utils.ChatUtils.COLLECTION_KEY
import com.team.oleg.funder.Utils.ChatUtils.DOCUMENT_KEY
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import org.jetbrains.anko.intentFor

class ChatMessageCompanyActivity : AppCompatActivity(), ChatMessageCompanyContract.View {

    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageCompanyContract.Presenter

    private lateinit var listAdapter: ChatMessageCompanyAdapter
    private var chatId: String? = null
    private var companyName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message_eo)
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference


        val data = intent.getParcelableExtra<Chat>(Utils.INTENT_PARCELABLE)
        chatId = data.chatId
        companyName = data.companyName
        chat_eo_name.text = data.eoName
        eo_chat_status.text = "status : ${data.bidderStatus}"
        storageRef?.child("userProfileImage/" + data.eoPhoto)?.downloadUrl?.addOnSuccessListener {
            Glide.with(this).load(it).into(eo_image_profile)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }




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
            presenter.cekOnline(edtAddMessage.text.toString(), chatId)
            edtAddMessage.text.clear()
        }

        setSupportActionBar(chat_message_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
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
        rvMessage.smoothScrollToPosition(messageList.size)
    }

    override fun showNoChat(active: Boolean) {
    }

    override fun showNewChat(chat: Message) {
        messageList.add(chat)
        listAdapter.notifyDataSetChanged()
        rvMessage.smoothScrollToPosition(messageList.size)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
        readMessage()
        presenter.setOnline(chatId, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setOnline(chatId, false)
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }

    private fun readMessage() {
        val map = HashMap<String, Any?>()
        map[ChatUtils.MESSAGE_STATUS_SENDING] = 200
        map[ChatUtils.CHAT_ID] = chatId
        map[ChatUtils.SENDER] = Utils.SENDER_EO

        firestoreChat.set(map)
            .addOnSuccessListener {
                //                Toast.makeText(this@ChatMessageCompanyActivity, "Message Sent", Toast.LENGTH_SHORT).show()
//                presenter.sendChat(message)
            }
            .addOnFailureListener { e ->
                Log.i("cek", e.localizedMessage)
            }
    }


    override fun setMessage(textMessage: String, status: Boolean) {
        val message = Message()
        message.chatId = chatId
        message.sender = Utils.SENDER_COMPANY
        message.messageSenderUsername = companyName
        message.message = textMessage
        message.messageStatus = ChatUtils.MESSAGE_STATUS_SENT
        message.messageRead = "0"

        if (status) {
            message.messageStatus = ChatUtils.MESSAGE_STATUS_READ
            message.messageRead = "1"
        }
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

//                            var mBuilder =
//                                chatId?.let {
//                                    NotificationCompat.Builder(this@ChatMessageCompanyActivity, it)
//                                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                                        .setContentTitle(data?.get(ChatUtils.USER_NAME).toString())
//                                        .setContentText(data?.get(ChatUtils.MESSAGE).toString())
//                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                }
//


                        } else if (data?.get(ChatUtils.MESSAGE_STATUS_SENDING) != null) {
                            if (data?.get(ChatUtils.CHAT_ID) == chatId
                                && data?.get(ChatUtils.SENDER) == Utils.SENDER_COMPANY
                            ) {
                                chatId?.let {
                                    presenter.realAllMessage(it)
                                }
                                for (i in 0 until messageList.size) {
                                    if (messageList[i].sender == Utils.SENDER_EO) {
                                        messageList[i].messageRead = "1"
                                    }
                                }

                                presenter.realAllMessage(chatId)
                                presenter.loadChat(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel(channelId: String, userName: String, message: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = userName
            val descriptionText = message
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat_company, menu)

//        supportActionBar?.setIcon(R.drawable.image_placeholder)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val data = intent.getParcelableExtra<Chat>(Utils.INTENT_PARCELABLE)
        Log.i("cek", data.bidderId)
        when (item?.itemId) {
            R.id.view_eo_profile -> {
                startActivity(
                    intentFor<EoProfileActivity>(
                        Utils.ID to data.eoId
                    )
                )
            }
            R.id.view_search -> {
                Toast.makeText(this, "EO SEARCH", Toast.LENGTH_SHORT).show()
            }

            R.id.view_deal_form -> {
                startActivity(
                    intentFor<DealFormActivity>(
                        Utils.BIDDER_ID to data.bidderId
                    )
                )
            }

            R.id.view_end_deal -> {
//                presenter.endDeal(data.bidderId)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
