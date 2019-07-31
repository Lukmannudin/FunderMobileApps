package com.team.oleg.funder.company.chatMessageCompany

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.data.Chat
import com.team.oleg.funder.data.Message
import com.team.oleg.funder.EOProfile.EoProfileActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.ChatUtils
import com.team.oleg.funder.utils.ChatUtils.CHAT_ID
import com.team.oleg.funder.utils.ChatUtils.COLLECTION_KEY
import com.team.oleg.funder.utils.ChatUtils.DOCUMENT_KEY
import com.team.oleg.funder.utils.Utils
import com.team.oleg.funder.company.CompanyActivity
import com.team.oleg.funder.company.DealForm.DealFormActivity
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
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
        rvMessage.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        presenter.loadChat(false)

        rvMessage.adapter = listAdapter
        realtimeUpdateListener()
        ivSendMessage.setOnClickListener {
            presenter.cekOnline(edtAddMessage.text.toString(), chatId)
            edtAddMessage.text.clear()
        }

        setSupportActionBar(chat_message_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        chat_message_toolbar.setOnClickListener {
            startActivity(
                intentFor<EoProfileActivity>(
                    Utils.USER_TYPE to Utils.SENDER_COMPANY,
                    Utils.ID to data.eoId
                )
            )
        }
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
        when (item?.itemId) {
            R.id.view_eo_profile -> {
                startActivity(
                    intentFor<EoProfileActivity>(
                        Utils.USER_TYPE to Utils.SENDER_COMPANY,
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
                        Utils.BIDDER_ID to data.bidderId,
                        Utils.ID to data.eoId
                    )
                )
            }

            R.id.view_end_deal -> {
                presenter.endDeal(data.bidderId)
//                startActivity(intentFor<ReviewEO>())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showDialog(message: String) {
        alertDialog(message)
    }

    private fun alertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        view.titleFillFormSubmitted.text = message
        view.btnSubmitFillForm.setOnClickListener {
            startActivity(intentFor<CompanyActivity>())
        }
        builder.setView(view)
        builder.show()
    }
}