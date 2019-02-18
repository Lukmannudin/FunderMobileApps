package com.team.oleg.funder.Data

import com.google.gson.annotations.SerializedName


data class Bank(
    @SerializedName("id_bank_eo")
    val idBankEo: String? = null,
    @SerializedName("account_rek")
    val accountRek: String? = null,
    @SerializedName("eo_id")
    val eoId: String? = null,
    @SerializedName("account_name")
    val accountName: String? = null,
    @SerializedName("bank_name")
    val bankName: String? = null,
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
    @SerializedName("verify")
    val verify: String? = null
    )
