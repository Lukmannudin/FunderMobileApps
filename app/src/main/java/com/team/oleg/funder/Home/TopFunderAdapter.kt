package com.team.oleg.funder.Home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.top_funder_list.view.*

class TopFunderAdapter(
    private val context: Context?,
    private val items: List<Sponsor>
) :
    RecyclerView.Adapter<TopFunderAdapter.TopFunderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFunderAdapter.TopFunderViewHolder {
        return TopFunderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.top_funder_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TopFunderViewHolder, position: Int) {
        holder.bindItem(context,items[position])
    }

    class TopFunderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivSponsorImage
        private val sponsorTitle = view.tvSponsorTitle
        private val sponsorCompany = view.tvSponsorCompany
        fun bindItem(context: Context?, items: Sponsor) {
            if (context != null) {
                sponsorImage.setImageURI(items.sponsorImage)
            }
            sponsorTitle.text = items.sponsorName
            sponsorCompany.text = items.sponsorCompany
        }
    }

}