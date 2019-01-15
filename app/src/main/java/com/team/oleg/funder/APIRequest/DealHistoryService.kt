package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.DealHistory
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DealHistoryService {
    @GET("bidder/{userid}")
    fun getDealHistory(@Path("userid") userid: String?):
            Observable<RootResponse<DealHistory>>
}