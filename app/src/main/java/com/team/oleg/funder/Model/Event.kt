package com.team.oleg.funder.Model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("event_id")
    @Expose
    val eventId: String? = null,
    @SerializedName("eo_id")
    @Expose
    val eoId: String? = null,
    @SerializedName("sponsor_id")
    @Expose
    val sponsorId: String? = null,
    @SerializedName("event_name")
    @Expose
    val eventName: String? = null,
    @SerializedName("event_date")
    @Expose
    val eventDate: String? = null,
    @SerializedName("event_speaker")
    @Expose
    val eventSpeaker: String? = null,
    @SerializedName("event_mp")
    @Expose
    val eventMp: String? = null,
    @SerializedName("event_desc")
    @Expose
    val eventDesc: String? = null,
    @SerializedName("event_proposal")
    @Expose
    val eventProposal: String? = null,
    @SerializedName("event_status")
    @Expose
    val eventStatus: String? = null
) : Parcelable

