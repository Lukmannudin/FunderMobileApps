package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Model.Chat
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApiChat {
    @GET("chat/eo/{eoId}")
        fun getChatEO(@Path("eoId") eoId : String):
            Observable<RootResponse<Chat>>
}