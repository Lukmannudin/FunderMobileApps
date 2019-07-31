package com.team.oleg.funder.data.source

import com.team.oleg.funder.data.Sponsor


interface SponsorsDataSource {

    interface LoadSponsorsCallback{

        fun onSponsorsLoaded(sponsor: List<Sponsor>)

        fun onDataNotAvailable()

    }

    interface GetSponsorCallback{

        fun onSponsorLoaded(sponsor:Sponsor)

        fun onDataNotAvailable()

    }

    fun getSponsors(callback: LoadSponsorsCallback)

    fun getSponsor(userid:String, callback: GetSponsorCallback)

    fun saveSponsor(user: Sponsor)

    fun refreshSponsors()

    fun deleteAllSponsors()

    fun deleteUserSponsor(sponsorid: String)
}