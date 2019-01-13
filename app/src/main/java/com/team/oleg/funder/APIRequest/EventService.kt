package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Response.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface EventService {
    @POST("event/add")
    fun setEvent(@Body event: Event):
            Observable<Response<String>>

}