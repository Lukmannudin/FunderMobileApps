package com.team.oleg.funder.Data.Source

import com.team.oleg.funder.APIRequest.SponsorService
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Service.ApiService
import io.reactivex.Flowable

object Remote {
    fun get():Flowable<List<Sponsor>>{
        val service:SponsorService = ApiService.sponsorService
            return  service.getSponsor()
                .map { it -> it.data }
    }
}