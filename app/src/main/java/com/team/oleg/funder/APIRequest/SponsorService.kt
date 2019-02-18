package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Response.RootResponse
import com.team.oleg.funder.Response.SponsorResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SponsorService {
    @GET("sponsor/")
    fun getSponsor(): Flowable<RootResponse<Sponsor>>

    @GET("sponsor/goal/")
    fun getSponsorTopFunder():
            Observable<RootResponse<Sponsor>>

    @GET("sponsor/{id}")
    fun getSponsorById(@Path("id") id: String):
            Observable<SponsorResponse>

    @GET("sponsor/search/")
    fun getSearchSponsor(@Query("keyword")keyword:String):
            Observable<RootResponse<Sponsor>>
}