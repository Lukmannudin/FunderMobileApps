package com.team.oleg.funder.EventOrganizer.ChatMessageEO

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.data.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.Utils
import kotlinx.android.synthetic.main.chat_message_receive.view.*
import kotlinx.android.synthetic.main.chat_message_sent.view.*


class ChatMessageEOAdapter(
    private val context: Context,
    private val items: MutableList<Message>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val MESSAGE_SENDER = Utils.SENDER_EO
    private val SENDER_VIEW_TYPE = 0
    private val RECEIVER_VIEW_TYPE = 1

    override fun getItemViewType(position: Int): Int {
        items[position].sender
        return when (items[position].sender) {
            MESSAGE_SENDER -> {
                SENDER_VIEW_TYPE
            }
            else -> {
                RECEIVER_VIEW_TYPE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            SENDER_VIEW_TYPE -> {
                return MessageSenderViewHolder(
                    inflater.inflate(R.layout.chat_message_sent, parent, false)
                )
            }
            RECEIVER_VIEW_TYPE -> {
                return MessageReceiverViewHolder(
                    inflater.inflate(R.layout.chat_message_receive, parent, false)
                )
            }

        }
        throw RuntimeException("View Type not defined")
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageSenderViewHolder -> {
                holder.bindItem(context, items[position])
            }
            is MessageReceiverViewHolder -> {
                holder.bindItem(context, items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MessageReceiverViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val tvMessage = view.tv_item_chat_message_receive

        fun bindItem(context: Context?, items: Message) {
            if (!items.message.equals("null")) {
                tvMessage.text = items.message
            } else {
                tvMessage.visibility = View.GONE
            }
        }
    }




    class MessageSenderViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val tvMessage = view.tv_item_chat_message_sent
        private val tvStatus = view.tv_item_chat_message_read

        fun bindItem(context: Context?, items: Message) {
            tvMessage.text = items.message
            if (items.messageRead.equals("1")){
                tvStatus.text = "Read"
            } else {
                tvStatus.text = "Sent"
            }

        }
    }

}