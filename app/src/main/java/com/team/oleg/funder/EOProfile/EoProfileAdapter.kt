package com.team.oleg.funder.EOProfile

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.track_record_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EoProfileAdapter(
    private val context: Context?,
    private val items: List<Bidder>?,
    private val listener: EoProfileActivity.BidderItemListener
) :
    RecyclerView.Adapter<EoProfileAdapter.TopFunderViewHolder>() {
    var yearCurrent = ""
    var yearSame = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFunderViewHolder {
        return TopFunderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.track_record_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: TopFunderViewHolder, position: Int) {

        Log.i("cek", items!![position].eventDate?.substringBefore("-").toString())
        if (!yearCurrent.equals(items[position].eventDate?.substringBefore("-").toString())) {
            yearCurrent = items[position].eventDate?.substringBefore("-").toString()
            yearSame = false
        } else {
            yearSame = true
        }

        holder.bindItem(context, items[position], yearSame, listener)
    }

    class TopFunderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var yearCurrent = ""
        private val year = view.yearTrackRecord
        private val date = view.dateTrackRecord
        private val eventName = view.eventNameTrackRecord
        private val desc = view.descTrackRecord
        fun bindItem(
            context: Context?,
            items: Bidder,
            yearString: Boolean,
            listener: EoProfileActivity.BidderItemListener
        ) {

            if (yearString){
                year.visibility = View.GONE
            } else {
                year.text = items.eventDate?.substringBefore("-")
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateN = LocalDate.parse(items.eventDate, DateTimeFormatter.ISO_DATE)
                date.text = "" + dateN.month.toString() + " " + dateN.dayOfMonth.toString()

            }

            eventName.text = items.eventName
            desc.text = items.eventDesc
            itemView.setOnClickListener {
                //                listener.onBidClicked(items)
            }
        }
    }

}