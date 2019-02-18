package com.team.oleg.funder.company.Request

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.chat_list.view.*

class RequestAdapter(
    private val context: Context?,
    private val items: List<Bidder>,
    private val listener: RequestFragment.BidderItemListener
) : RecyclerView.Adapter<RequestAdapter.BidderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidderViewHolder {
        return BidderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BidderViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    class BidderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val companyImage = view.ivChatList
        private val titleChat = view.tvTitleChatList
        private val eventName = view.tvChatEventName
        private val messageChat = view.tvMessageChatList
        private val dateChat = view.tvChatDateList
        private val unreadMessage = view.tvUnreadMessage

        fun bindItem(
            context: Context?,
            bidder: Bidder,
            listener: RequestFragment.BidderItemListener
        ) {
            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            context?.let {
                Glide.with(it).load(
                    BuildConfig.BASE_URL + "uploads/photo/eo_photo/" + bidder.eoPhoto
                ).into(companyImage)
            }

            Log.i("cekcok",bidder.eoPhoto)
            titleChat.text = bidder.eoName
            eventName.text = bidder.eventName
            messageChat.text = ""
            unreadMessage.text = ""
            messageChat.text = ""
            dateChat.text = bidder.bidderDate
            itemView.setOnClickListener {
                listener.onBidderClick(bidder)
            }

            unreadMessage.visibility = View.GONE


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