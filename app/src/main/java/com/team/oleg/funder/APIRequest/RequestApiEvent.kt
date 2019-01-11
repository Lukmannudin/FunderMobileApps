package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Model.Company
import com.team.oleg.funder.Model.Event
import com.team.oleg.funder.Model.User
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestApiEvent {
    @POST("event/")
    fun setEvent(@Body event: Event):
            Observable<RootResponse<Event>>

}