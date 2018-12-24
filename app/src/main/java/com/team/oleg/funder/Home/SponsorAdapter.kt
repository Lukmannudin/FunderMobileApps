package com.team.oleg.funder.Home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Dummy.DummyAuction
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.auction_list.view.*
import kotlinx.android.synthetic.main.rv_heading_auction.view.*


class SponsorAdapter(
    private val context: Context?,
    topFunder: List<Sponsor>,
    auction: List<Sponsor>,
    private val listener: MainHomeFragment.SponsorItemListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TOP_FUNDER_VIEW_TYPE = 0
    private val AUCTION_VIEW_TYPE = 1
    private val OVERSIZE = 1

    var items: List<Sponsor> = topFunder
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    var sponsor: List<Sponsor> = auction
        set(sponsor) {
            field = sponsor
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TOP_FUNDER_VIEW_TYPE else AUCTION_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TOP_FUNDER_VIEW_TYPE -> {
                return TopFunderViewHolder(
                    inflater.inflate(R.layout.rv_heading_auction, parent, false)
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
            holder.bindItem(context, items)
        } else if (holder is AuctionViewHolder) {
            holder.bindItem(context, sponsor[position - OVERSIZE], listener)
        }
    }

    override fun getItemCount(): Int {
        return sponsor.size + OVERSIZE
    }

    class TopFunderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TopFunderRecyclerView = view.rvTopFunder

        fun bindItem(context: Context?, items: List<Sponsor>) {
            TopFunderRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            TopFunderRecyclerView.adapter = TopFunderAdapter(context, DummyAuction.getListData())
            Log.i("positionaing","bindItem:"+items.size)
        }
    }


    class AuctionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivAuctionList
        private val sponsorTitle = view.tvTitleAuctionList
        private val sponsorDescription = view.tvContentAuctionList
        private val sponsorCompanyName = view.tvAuctionCompanyList
        private val sponsorDatePost = view.tvDateAuctionList

        fun bindItem(context: Context?, sponsor: Sponsor, listener: MainHomeFragment.SponsorItemListener) {
            val dummyImage ="https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            context?.let { Glide.with(it).load(dummyImage).into(sponsorImage) }
            sponsorTitle.text = sponsor.sponsorName
            sponsorDescription.text = sponsor.sponsorDesc
            sponsorCompanyName.text = sponsor.companyName
            sponsorDatePost.text = sponsor.dateline
            itemView.setOnClickListener {
                listener.onSponsorClick(sponsor)
            }
        }
    }

}