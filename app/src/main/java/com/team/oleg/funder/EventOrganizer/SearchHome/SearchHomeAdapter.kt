package com.team.oleg.funder.EventOrganizer.SearchHome

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.auction_list.view.*


class SearchHomeAdapter(
    private val context: Context?,
    private val sponsor: List<Sponsor>,
    private val listener: SearchHomeActivity.SponsorClickListener
) :
    RecyclerView.Adapter<SearchHomeAdapter.SearchViewHolder>() {

    override fun getItemCount(): Int {
        return sponsor.size
    }

    override fun onBindViewHolder(holder: SearchHomeAdapter.SearchViewHolder, position: Int) {
        holder.bindItem(context,sponsor[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(
            inflater.inflate(R.layout.auction_list, parent, false)
        )
    }


    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivAuctionList
        private val sponsorTitle = view.tvTitleAuctionList
        private val sponsorDescription = view.tvContentAuctionList
        private val sponsorCompanyName = view.tvAuctionCompanyList
        private val sponsorDatePost = view.tvDateAuctionList

        fun bindItem(context: Context?, sponsor: Sponsor,listener: SearchHomeActivity.SponsorClickListener) {

            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            context?.let {
                Glide.with(it).load(
                    BuildConfig.BASE_URL
                            + "uploads/img/img_sponsor/${sponsor.sponsorImage}"
                )
                    .into(sponsorImage)
            }
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
