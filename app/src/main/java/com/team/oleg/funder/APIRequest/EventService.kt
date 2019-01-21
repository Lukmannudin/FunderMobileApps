package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Response.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {
    @POST("event/add")
    fun setEvent(@Body event: Event):
            Observable<Response<String>>

    @POST("event/{eventId}")
    fun getEvent(@Path("eventId") eventId: String?):
            Observable<Response<Event>>

    @POST("bidder/accepted/{bidderId}")
    fun setBidderAccepted(@Path("bidderId") bidderId: String?):
            Observable<Response<String>>

    @POST("bidder/rejected/{bidderId}")
    fun setBidderRejected(@Path("bidderId") bidderId: String?):
            Observable<Response<String>>

}