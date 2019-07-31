package com.team.oleg.funder.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Sponsor(
    @SerializedName("sponsor_id")

    val sponsorId: String? = null,
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

