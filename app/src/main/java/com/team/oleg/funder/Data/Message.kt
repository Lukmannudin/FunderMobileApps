package com.team.oleg.funder.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    @SerializedName("message_id")
    val messageId: String? = null,

    @SerializedName("chat_id")
    val chatId: String? = null,

    @SerializedName("sender")
    val sender: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("message_time")
    val messageTime: String? = null,

    @SerializedName("message_status")
    val messageStatus: String? = null,

    @SerializedName("message_read")
    val messageRead: String? = null
) : Parcelable

