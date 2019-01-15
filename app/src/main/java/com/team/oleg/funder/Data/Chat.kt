package com.team.oleg.funder.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    @SerializedName("chat_id")
    val chatId: String? = null,

    @SerializedName("eo_id")
    val eoId: String? = null,

    @SerializedName("company_id")
    val companyId: String? = null,

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
    val companyPhoto: String? = null,

    @SerializedName("message_id")
    val messageId: String? = null,

    @SerializedName("sender")
    val sender: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("message_time")
    val messageTime: String? = null,

    @SerializedName("message_status")
    val messageStatus: String? = null
) : Parcelable
