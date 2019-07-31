package com.team.oleg.funder.company.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.data.Chat
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.chat_list.view.*

class ChatCompanyAdapter(
    private val context: Context?,
    private val items: List<Chat>,
    private val listener: ChatFragment.ChatItemListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<ChatCompanyAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            holder.bindItem(context, items[position], listener)

    }

    class ChatViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val companyImage = view.ivChatList
        private val titleChat = view.tvTitleChatList
        private val eventName = view.tvChatEventName
        private val messageChat = view.tvMessageChatList
        private val dateChat = view.tvChatDateList
        private val unreadMessage = view.tvUnreadMessage

        fun bindItem(
            context: Context?,
            chat: Chat,
            listener: ChatFragment.ChatItemListener
        ) {
            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
//            context?.let {
//                Glide.with(it).load(
//                    BuildConfig.BASE_URL + "uploads/photo/eo_photo/" + chat.eoPhoto
//                ).into(companyImage)
//            }

            val storage = FirebaseStorage.getInstance()
            val storageRef: StorageReference? = storage.reference


            storageRef?.child("userProfileImage/" + chat.eoPhoto)?.downloadUrl?.addOnSuccessListener {
                context?.let { it1 -> Glide.with(it1).load(it).into(companyImage) }
            }?.addOnFailureListener { Log.i("file", it.localizedMessage) }

            titleChat.text = chat.eoName
            eventName.text = chat.eventName
            messageChat.text = chat.message

            if (unreadMessage.equals("0")){
                unreadMessage.visibility = View.GONE
            }
            unreadMessage.text = chat.unread
            messageChat.text = chat.lastMessage
            itemView.setOnClickListener {
                listener.onChatClick(chat)
            }
            dateChat.text = chat.messageTime


//            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
//
//            val currentDate = sdf.format(Date()).split(" ")[0]
//            val currentDateTanggal = chat.messageTime.toString().split(" ")[1].substring(0, 5)
//            val currentDateChat = chat.messageTime.toString().split(" ")[0]
//
//            if (currentDate == currentDateChat) {
//                dateChat.text = currentDateTanggal
//            } else {
//                dateChat.text = currentDateChat
//            }
        }
    }
}