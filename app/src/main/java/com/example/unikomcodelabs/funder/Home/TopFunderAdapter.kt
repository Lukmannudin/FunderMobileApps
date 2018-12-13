package com.example.unikomcodelabs.funder.Home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unikomcodelabs.funder.Grocery
import com.example.unikomcodelabs.funder.R
import com.example.unikomcodelabs.funder.TopFunder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.top_funder_list.view.*
import org.jetbrains.anko.find

class TopFunderAdapter (private val items : List<TopFunder>) :
    RecyclerView.Adapter<TopFunderAdapter.TopFunderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFunderViewHolder {
        return TopFunderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.top_funder_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TopFunderViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    class TopFunderViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val sponsorImage = view.ivSponsorImage
        private val sponsorTitle = view.tvSponsorTitle
        private val sponsorCompany = view.tvSponsorCompany
        fun bindItem(items: TopFunder){
            Picasso.get().load(items.sponsorImage).into(sponsorImage)
            sponsorTitle.text = items.sponsorTitle
            sponsorCompany.text = items.sponsorCompany
        }
    }
}