package com.team.oleg.funder.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("event_id")
    @Expose
    var eventId: String? = null,
    @SerializedName("eo_id")
    @Expose
    var eoId: String? = null,
    @SerializedName("sponsor_id")
    @Expose
    var sponsorId: String? = null,
    @SerializedName("event_name")
    @Expose
    var eventName: String? = null,
    @SerializedName("event_date")
    @Expose
    var eventDate: String? = null,
    @SerializedName("event_speaker")
    @Expose
    var eventSpeaker: String? = null,
    @SerializedName("event_mp")
    @Expose
    var eventMp: String? = null,
    @SerializedName("event_desc")
    @Expose
    var eventDesc: String? = null,
    @SerializedName("event_proposal")
    @Expose
    var eventProposal: String? = null,
    @SerializedName("event_status")
    @Expose
    var eventStatus: String? = null,
    @SerializedName("event_dana")
    @Expose
    var eventDana: String? = null
) : Parcelable

