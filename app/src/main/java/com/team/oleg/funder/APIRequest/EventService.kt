package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface EventService {
    @POST("event/")
    fun setEvent(@Body event: Event):
            Observable<RootResponse<Event>>

}