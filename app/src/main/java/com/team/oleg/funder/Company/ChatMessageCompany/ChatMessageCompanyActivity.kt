package com.team.oleg.funder.Company.ChatMessageCompany

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.team.oleg.funder.Company.DealForm.DealFormActivity
import com.team.oleg.funder.Company.EOProfile.EoProfileActivity
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.ChatUtils
import com.team.oleg.funder.Utils.ChatUtils.CHAT_ID
import com.team.oleg.funder.Utils.ChatUtils.COLLECTION_KEY
import com.team.oleg.funder.Utils.ChatUtils.DOCUMENT_KEY
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_chat_message_eo.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class ChatMessageCompanyActivity : AppCompatActivity(), ChatMessageCompanyContract.View {

    private val messageList: MutableList<Message> = mutableListOf()

    override lateinit var presenter: ChatMessageCompanyContract.Presenter

    private lateinit var listAdapter: ChatMessageCompanyAdapter
    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "EO name"
//        supportActionBar?.subtitle = "Status: Not Transfered"
//        supportActionBar?.setDisplayUseLogoEnabled(true)
//        supportActionBar?.setIcon(R.drawable.image_placeholder)



        setContentView(R.layout.activity_chat_message_eo)
        chatId = intent.getStringExtra(ChatUtils.CHAT_ID)



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
            presenter.cekOnline(edtAddMessage.text.toString(),chatId)
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
        presenter.setOnline(chatId,true)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setOnline(chatId,false)
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }

    private fun readMessage(){
        val map = HashMap<String,Any?>()
        map[ChatUtils.MESSAGE_STATUS_SENDING] = 200
        map[ChatUtils.CHAT_ID] = chatId
        map[ChatUtils.SENDER] = Utils.SENDER_EO

        firestoreChat.set(map)
            .addOnSuccessListener {
//                Toast.makeText(this@ChatMessageCompanyActivity, "Message Sent", Toast.LENGTH_SHORT).show()
//                presenter.sendChat(message)
            }
            .addOnFailureListener { e ->
                Log.i("cek",e.localizedMessage)
            }
    }


    override fun setMessage(textMessage: String, status: Boolean) {
        val message = Message()
        message.chatId = chatId
        message.sender = Utils.SENDER_COMPANY
        message.message = textMessage
        message.messageStatus = ChatUtils.MESSAGE_STATUS_SENT
        message.messageRead = "0"

        if (status){
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
                        }else if(data?.get(ChatUtils.MESSAGE_STATUS_SENDING) != null){
                            if (data?.get(ChatUtils.CHAT_ID) == chatId
                            && data?.get(ChatUtils.SENDER) == Utils.SENDER_COMPANY){
                                chatId?.let {
                                    presenter.realAllMessage(it)
                                }
                                for (i in 0 until messageList.size){
                                    if (messageList[i].sender == Utils.SENDER_EO){
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat_company,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.view_eo_profile -> {
                startActivity(intentFor<EoProfileActivity>())
            }
            R.id.view_search -> {
                Toast.makeText(this,"EO SEARCH",Toast.LENGTH_SHORT).show()
            }

            R.id.view_deal_form -> {
                startActivity(intentFor<DealFormActivity>())
            }

            R.id.view_end_deal -> {
                Toast.makeText(this,"EO END DEAL",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
