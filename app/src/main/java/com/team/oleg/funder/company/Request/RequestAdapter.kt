package com.team.oleg.funder.company.Request

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.data.Bidder
import kotlinx.android.synthetic.main.chat_list.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RequestAdapter(
    private val context: Context?,
    private val items: List<Bidder>,
    private val listener: RequestFragment.BidderItemListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<RequestAdapter.BidderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidderViewHolder {
        return BidderViewHolder(
            LayoutInflater.from(parent.context).inflate(com.team.oleg.funder.R.layout.chat_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BidderViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    class BidderViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
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
            val storage = FirebaseStorage.getInstance()
            val storageRef: StorageReference? = storage.reference
            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            storageRef?.child("userProfileImage/" + bidder.eoPhoto)?.downloadUrl?.addOnSuccessListener {
                context?.let { it1 -> Glide.with(it1).load(it).into(companyImage) }
            }?.addOnFailureListener { Log.i("file", it.localizedMessage) }

            titleChat.text = bidder.eoName
            eventName.text = bidder.eventName
            messageChat.text = ""
            unreadMessage.text = ""
            messageChat.text = ""
            dateChat.text = bidder.bidderDate
            itemView.setOnClickListener {
                listener.onBidderClick(bidder)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val todayDate = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted = todayDate.format(formatter)
            }

            unreadMessage.visibility = View.GONE

        }
    }
}