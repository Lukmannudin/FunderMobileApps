package com.team.oleg.funder.Home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.R
import com.team.oleg.funder.Sponsor
import com.team.oleg.funder.TopFunder
import kotlinx.android.synthetic.main.auction_list.view.*
import kotlinx.android.synthetic.main.heading_home_main.view.*


class SponsorAdapter(
    private val context: Context?,
    private val items: List<TopFunder>,
    private val sponsor: List<Sponsor>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TOP_FUNDER_VIEW_TYPE = 0
    private val AUCTION_VIEW_TYPE = 1
    private val OVERSIZE = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TOP_FUNDER_VIEW_TYPE else AUCTION_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TOP_FUNDER_VIEW_TYPE -> {
                return TopFunderViewHolder(
                    inflater.inflate(R.layout.heading_home_main, parent, false)
                )
            }

            AUCTION_VIEW_TYPE -> {
                return AuctionViewHolder(
                    inflater.inflate(R.layout.auction_list, parent, false)
                )
            }
        }
        throw RuntimeException("View Type not defined")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TopFunderViewHolder) {
            holder.init(context, items)
        } else if (holder is AuctionViewHolder) {
            holder.bindItem(context, sponsor[position - OVERSIZE])
        }
    }

    override fun getItemCount(): Int {
        return sponsor.size + OVERSIZE
    }

    class TopFunderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TopFunderRecyclerView = view.rvTopFunder

        fun init(context: Context?, items: List<TopFunder>) {
            TopFunderRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            TopFunderRecyclerView.adapter = TopFunderAdapter(context, items)
        }
    }


    class AuctionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivAuctionList
        private val sponsorTitle = view.tvTitleAuctionList
        private val sponsorDescription = view.tvContentAuctionList
        private val sponsorCompanyName = view.tvAuctionCompanyList
        private val sponsorDatePost = view.tvDateAuctionList

        fun bindItem(context: Context?, sponsor: Sponsor) {
            context?.let { Glide.with(it).load(sponsor.sponsorImage).into(sponsorImage) }
            sponsorTitle.text = sponsor.sponsorName
            Log.i("cek", "bindItem")
            sponsorDescription.text = sponsor.sponsorDesc
            sponsorCompanyName.text = sponsor.sponsorCompany
            sponsorDatePost.text = sponsor.sponsorDate
        }
    }

}