package com.team.oleg.funder.DealHistory

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.Model.DealHistory
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.deal_history_list.view.*

class DealHistoryAdapter(
    private val context: Context?,
    private val items: List<DealHistory>,
    private val listener: MainDealHistoryFragment.DealHistoryItemListener
) : RecyclerView.Adapter<DealHistoryAdapter.DealHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealHistoryViewHolder {
        return DealHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.deal_history_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DealHistoryViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    class DealHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivDealHistorySponsor
        private val bidStatus = view.tvBidStatus
        private val bidName = view.tvBidName
        private val companyName = view.tvDealHistoryCompanyName
        private val bidDate = view.tvDateDealHistory
        fun bindItem(
            context: Context?,
            dealHistory: DealHistory,
            listener: MainDealHistoryFragment.DealHistoryItemListener
        ) {
            context?.let {
                Glide.with(it).load(
                    BuildConfig.BASE_URL
                            + "uploads/img/img_sponsor/${dealHistory.sponsorImage}"
                )
                    .into(sponsorImage)
            }

            bidStatus.text = dealHistory.bidderStatus
            Log.i("ceko", dealHistory.bidderStatus)
            when (dealHistory.bidderStatus?.toLowerCase()) {
                "canceled" -> {
                    bidStatus.setTextColor(Color.parseColor("#b67a36"))
                }
                "rejected" -> {
                    bidStatus.setTextColor(Color.parseColor("#b63636"))
                }
                "accepted" -> {
                    bidStatus.setTextColor(Color.parseColor("#53b636"))
                }
            }
            bidName.text = dealHistory.eventName
            companyName.text = dealHistory.companyName
            bidDate.text = dealHistory.dateline
        }
    }
}