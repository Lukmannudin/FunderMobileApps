package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.Data.Message
import com.team.oleg.funder.Response.ChatUnreadResponse
import com.team.oleg.funder.Response.Response
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    @GET("chat/eo/{eoId}")
    fun getChatEO(@Path("eoId") eoId: String?):
            Observable<RootResponse<Chat>>

    @GET("chat/company/{companyId}")
    fun getChatCompany(@Path("companyId") companyId: String?):
            Observable<RootResponse<Chat>>

    @GET("chat/unread/{eoId}")
    fun getUnreadChatEO(@Path("eoId") eoId: String):
            Observable<ChatUnreadResponse>

    @GET("message/{chatId}")
    fun getMessageEO(@Path("chatId") chatId: String?):
            Observable<RootResponse<Message>>

    @POST("message/")
    fun sendMessage(@Body message: Message):
            Observable<Response<String>>

    @POST("message/readall/{chatId}")
    fun readAllMessage(@Path("chatId") chatId: String?):
            Observable<Response<String>>

    @POST("chat/openEO/{chatId}")
    fun setOnlineEO(@Path("chatId") chatId: String?):
            Observable<Response<String>>

    @POST("chat/closeEO/{chatId}")
    fun setOfflineEO(@Path("chatId") chatId: String?):
            Observable<Response<String>>

    @POST("chat/openCompany/{chatId}")
    fun setOnlineCompany(@Path("chatId") chatId: String?):
            Observable<Response<String>>

    @POST("chat/closeCompany/{chatId}")
    fun setOfflineCompany(@Path("chatId") chatId: String?):
            Observable<Response<String>>

    @GET("chat/cekOnlineEO/{chatId}")
    fun cekOnlineEO(@Path("chatId") chatId: String?):
            Observable<Response<Boolean>>

    @GET("chat/cekOnlineCompany/{chatId}")
    fun cekOnlineCompany(@Path("chatId") chatId: String?):
            Observable<Response<Boolean>>

}