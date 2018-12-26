package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Model.Chat
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit.http.GET
import retrofit.http.Path

interface RequestApiChat {
    @GET("chat/eo/{eoId}")
//    fun getChatEO(@Path("eoId") eoId : String):
//            Observable<RootResponse<Chat>>
    fun getChatEO():
            Observable<RootResponse<Chat>>
}