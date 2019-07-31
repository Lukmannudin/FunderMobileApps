package com.team.oleg.funder.apirequest

import com.team.oleg.funder.data.Bidder
import com.team.oleg.funder.data.DealHistory
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DealHistoryService {
    @GET("bidder/{userid}")
    fun getDealHistory(@Path("userid") userid: String?):
            Observable<RootResponse<DealHistory>>

    @GET("bidder/company/{companyId}")
    fun getBidder(@Path("companyId") companyId: String?):
            Observable<RootResponse<Bidder>>
}