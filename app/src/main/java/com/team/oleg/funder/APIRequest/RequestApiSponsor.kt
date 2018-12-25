package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Response.SponsorResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RequestApiSponsor {
    @GET("sponsor/")
    fun getSponsor():
            Observable<SponsorResponse>

    @GET("sponsor/goal/")
    fun getSponsorTopFunder():
            Observable<SponsorResponse>
}