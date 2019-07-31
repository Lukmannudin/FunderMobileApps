package com.team.oleg.funder.EventOrganizer.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.data.Sponsor
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.top_funder_list.view.*

class TopFunderAdapter(
    private val context: Context?,
    private val items: List<Sponsor>,
    private val listener: HomeFragment.SponsorItemListener
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TopFunderAdapter.TopFunderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFunderViewHolder {
        return TopFunderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.top_funder_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TopFunderViewHolder, position: Int) {
        holder.bindItem(context,items[position], listener)
    }

    class TopFunderViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val sponsorImage = view.ivSponsorImage
        private val sponsorTitle = view.tvSponsorTitle
        private val sponsorCompany = view.tvSponsorCompany
        fun bindItem(context: Context?, items: Sponsor, listener: HomeFragment.SponsorItemListener) {
            val dummyImage ="https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            if (context != null) {
                sponsorImage.setImageURI(BuildConfig.BASE_URL+"uploads/img/img_sponsor/${items.sponsorImage}")
            }
            sponsorTitle.text = items.sponsorName
            sponsorCompany.text = items.companyName
            itemView.setOnClickListener {
                listener.onSponsorClick(items)
            }
        }
    }

}