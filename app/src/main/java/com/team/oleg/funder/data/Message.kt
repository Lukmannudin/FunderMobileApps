package com.team.oleg.funder.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    @SerializedName("message_id")
    var messageId: String? = null,

    @SerializedName("chat_id")
    var chatId: String? = null,

    @SerializedName("sender")
    var sender: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("message_time")
    var messageTime: String? = null,

    @SerializedName("message_status")
    var messageStatus: String? = null,

    @SerializedName("message_read")
    var messageRead: String? = null,

    var messageSenderUsername: String? = null
) : Parcelable

