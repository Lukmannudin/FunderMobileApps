package com.team.oleg.funder.APIRequest

import android.net.Uri
import com.team.oleg.funder.BuildConfig

object APISponsors {
    fun getSponsor():String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("sponsor")
            .build()
            .toString()
    }
}