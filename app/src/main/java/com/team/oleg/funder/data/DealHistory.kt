package com.team.oleg.funder.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DealHistory(
    @SerializedName("eo_id")
    val eoId: String? = null,

    @SerializedName("eo_email")
    val eoEmail: String? = null,

    @SerializedName("eo_password")
    val eoPassword: String? = null,

    @SerializedName("eo_name")
    val eoName: String? = null,

    @SerializedName("eo_point")
    val eoPoint: String? = null,

    @SerializedName("eo_vision")
    val eoVision: String? = null,

    @SerializedName("eo_mission")
    val eoMission: String? = null,

    @SerializedName("eo_photo")
    val eoPhoto: String? = null,

    @SerializedName("event_id")
    val eventId: String? = null,

    @SerializedName("sponsor_id")
    val sponsorId: String? = null,

    @SerializedName("event_name")
    val eventName: String? = null,

    @SerializedName("event_date")
    val eventDate: String? = null,

    @SerializedName("event_speaker")
    val eventSpeaker: String? = null,

    @SerializedName("event_mp")
    val eventMp: String? = null,

    @SerializedName("event_desc")
    val eventDesc: String? = null,

    @SerializedName("event_proposal")
    val eventProposal: String? = null,

    @SerializedName("event_status")
    val eventStatus: String? = null,

    @SerializedName("bidder_id")
    val bidderId: String? = null,

    @SerializedName("bidder_date")
    val bidderDate: String? = null,

    @SerializedName("bidder_status")
    val bidderStatus: String? = null,

    @SerializedName("company_id")
    val companyId: String? = null,

    @SerializedName("sponsor_name")
    val sponsorName: String? = null,

    @SerializedName("sponsor_desc")
    val sponsorDesc: String? = null,

    @SerializedName("sponsor_image")
    val sponsorImage: String? = null,

    @SerializedName("dateline")
    val dateline: String? = null,

    @SerializedName("sponsor_req")
    val sponsorReq: String? = null,

    @SerializedName("proposal_example")
    val proposalExample: String? = null,

    @SerializedName("goal")
    val goal: String? = null,
    @SerializedName("company_email")
    @Expose
    val companyEmail: String? = null,

    @SerializedName("company_password")
    val companyPassword: String? = null,

    @SerializedName("company_name")
    val companyName: String? = null,

    @SerializedName("company_vision")
    val companyVision: String? = null,

    @SerializedName("company_mission")
    val companyMission: String? = null,

    @SerializedName("company_photo")
    val companyPhoto: String? = null

) : Parcelable
