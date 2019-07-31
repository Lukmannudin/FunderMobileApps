package com.team.oleg.funder.api

import com.team.oleg.funder.data.Sponsor
import com.team.oleg.funder.data.source.SponsorsDataSource

object UserService : SponsorsDataSource {
    override fun getSponsors(callback: SponsorsDataSource.LoadSponsorsCallback) {
    }

    override fun getSponsor(userid: String, callback: SponsorsDataSource.GetSponsorCallback) {
    }

    override fun saveSponsor(user: Sponsor) {
    }

    override fun refreshSponsors() {
    }

    override fun deleteAllSponsors() {
    }

    override fun deleteUserSponsor(sponsorid: String) {
    }

}