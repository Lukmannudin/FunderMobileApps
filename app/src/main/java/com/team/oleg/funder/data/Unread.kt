package com.team.oleg.funder.data

import com.google.gson.annotations.SerializedName

data class Unread(
    @SerializedName("unread")
    val unread: String? = null
)

