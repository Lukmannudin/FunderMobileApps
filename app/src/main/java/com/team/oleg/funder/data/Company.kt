package com.team.oleg.funder.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    @SerializedName("company_id")
    var companyId: String? = null,

    @SerializedName("company_email")
    var companyEmail: String? = null,

    @SerializedName("company_password")
    var companyPassword: String? = null,

    @SerializedName("company_name")
    var companyName: String? = null,

    @SerializedName("company_vision")
    var companyVision: String? = null,

    @SerializedName("company_mission")
    var companyMission: String? = null,

    @SerializedName("company_photo")
    var companyPhoto: String? = null
) : Parcelable
