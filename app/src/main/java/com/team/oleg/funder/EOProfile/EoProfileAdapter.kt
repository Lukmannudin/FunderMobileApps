package com.team.oleg.funder.EOProfile

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Data.Bidder
import kotlinx.android.synthetic.main.track_record_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EoProfileAdapter(
    private val context: Context?,
    private val items: List<Bidder>?,
    private val listener: EoProfileActivity.BidderItemListener
) :
    RecyclerView.Adapter<EoProfileAdapter.TopFunderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFunderViewHolder {
        return TopFunderViewHolder(
            LayoutInflater.from(context).inflate(com.team.oleg.funder.R.layout.track_record_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: TopFunderViewHolder, position: Int) {
        holder.bindItem(context, items!![position], listener)
    }

    class TopFunderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var yearCurrent = ""
        private val year = view.yearTrackRecord
        private val date = view.dateTrackRecord
        private val eventName = view.eventNameTrackRecord
        private val desc = view.descTrackRecord
        fun bindItem(context: Context?, items: Bidder, listener: EoProfileActivity.BidderItemListener) {
            val dummyImage =
                "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg"
            if (context != null) {
//                sponsorImage.setImageURI(BuildConfig.BASE_URL+"uploads/img/img_sponsor/${items.sponsorImage}")
            }

            if (yearCurrent != items.eventDate?.substringBefore("-").toString()){
                year.text = items.eventDate?.substringBefore("-")
                yearCurrent = items.eventDate?.substringBefore("-").toString()
                Log.i("year",yearCurrent+":"+items.eventDate?.substringBefore("-"))
            }  else{
                year.visibility = View.GONE
                yearCurrent = items.eventDate?.substringBefore("-").toString()
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateN =  LocalDate.parse(items.eventDate, DateTimeFormatter.ISO_DATE)
                date.text = ""+ dateN.month.toString()+" " + dateN.dayOfMonth.toString()

            }

            eventName.text = items.eventName
            desc.text = items.eventDesc
            itemView.setOnClickListener {
                //                listener.onBidClicked(items)
            }
        }
    }

}