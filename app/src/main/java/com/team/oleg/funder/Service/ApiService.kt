package com.team.oleg.funder.Service

import com.team.oleg.funder.APIRequest.*
import com.team.oleg.funder.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiService {
    private val client = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val sponsorService: SponsorService = client.create(SponsorService::class.java)
    val chatService: ChatService = client.create(ChatService::class.java)
    val dealHistoryService: DealHistoryService = client.create(DealHistoryService::class.java)
    val eventService: EventService = client.create(EventService::class.java)
    val userService: UserService = client.create(UserService::class.java)
    val utilService: UtilsService = client.create(UtilsService::class.java)
}