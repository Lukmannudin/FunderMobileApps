package com.team.oleg.funder.Company.ChatMessageCompany

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Model.Message
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.chat_message_receive.view.*
import kotlinx.android.synthetic.main.chat_message_sent.view.*


class ChatMessageCompanyAdapter(
    private val context: Context,
    private val items: List<Message>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MESSAGE_SENDER = Utils.SENDER_EO
    private val MESSAGE_RECEIVER = Utils.SENDER_COMPANY

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.i("cek",items[0].message)
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


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

    class MessageReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMessage = view.tv_item_chat_message_receive

        fun bindItem(context: Context?, items: Message) {
            Log.i("cek receiver",items.message)
            tvMessage.text = items.message
        }
    }


    class MessageSenderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMessage = view.tv_item_chat_message_sent

        fun bindItem(context: Context?, items: Message) {
            Log.i("cek sender",items.message)

            tvMessage.text = items.message
        }
    }

}