package com.team.oleg.funder.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    @SerializedName("eo_id")
    var eoId: String? = null,

    @SerializedName("eo_email")
    var eoEmail: String? = null,

    @SerializedName("eo_password")
    var eoPassword: String? = null,

    @SerializedName("eo_name")
    var eoName: String? = null,

    @SerializedName("eo_point")
    var eoPoint: String? = null,

    @SerializedName("eo_vision")
    var eoVision: String? = null,

    @SerializedName("eo_mission")
    var eoMission: String? = null,

    @SerializedName("eo_photo")
    var eoPhoto: String? = null
) : Parcelable