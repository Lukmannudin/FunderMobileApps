package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RequestApiSponsor {
    @GET("sponsor/")
    fun getSponsor():
            Observable<RootResponse<Sponsor>>

    @GET("sponsor/goal/")
    fun getSponsorTopFunder():
            Observable<RootResponse<Sponsor>>
}