package com.team.oleg.funder.EventOrganizer.Chat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.chat_list.view.*

class ChatEOAdapter(
    private val context: Context?,
    private val items: List<Chat>,
    private val listener: MainChatFragment.chatItemListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<ChatEOAdapter.ChatViewHolder>() {

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
            listener: MainChatFragment.chatItemListener
        ) {
            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"

            context?.let {
                Glide.with(it).load(
                    BuildConfig.BASE_URL + "uploads/photo/company_photo/"+chat.companyPhoto
                ).into(companyImage)
            }
            titleChat.text = chat.companyName
            eventName.text = chat.eventName
            if (unreadMessage.equals("0")){
                unreadMessage.visibility = View.GONE
            }

            unreadMessage.text = chat.unread
            dateChat.text = chat.messageTime
            if (chat.unread.equals("0")){
                unreadMessage.visibility = View.GONE
            } else {
                unreadMessage.visibility = View.VISIBLE
            }

            messageChat.text = chat.lastMessage
            itemView.setOnClickListener {
                listener.onChatClick(chat)
            }

        }
    }
}