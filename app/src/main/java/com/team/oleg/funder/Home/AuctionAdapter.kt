package com.team.oleg.funder.Home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.R
import com.team.oleg.funder.Sponsor
import kotlinx.android.synthetic.main.auction_list.view.*

class AuctionAdapter(private val context: Context?, private val items: List<Sponsor>) :
    RecyclerView.Adapter<SponsorAdapter.AuctionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorAdapter.AuctionViewHolder {

        return SponsorAdapter.AuctionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.auction_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: SponsorAdapter.AuctionViewHolder, position: Int) {
        viewHolder.bindItem(context, items[position])
    }

    class AuctionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val sponsorImage = view.ivAuctionList
        private val sponsorTitle = view.tvTitleAuctionList
        private val sponsorDescription = view.tvContentAuctionList
        private val sponsorCompanyName = view.tvAuctionCompanyList
        private val sponsorDatePost = view.tvDateAuctionList

        fun bindItem(context: Context?, items: Sponsor) {
            context?.let { Glide.with(it).load(items.sponsorImage).into(sponsorImage) }
            sponsorTitle.text = items.sponsorName
            sponsorDescription.text = items.sponsorDesc
            sponsorCompanyName.text = items.sponsorCompany
            sponsorDatePost.text = items.sponsorDate
        }
    }
}