package com.team.oleg.funder.Service

import com.google.gson.Gson
import com.team.oleg.funder.APIRequest.RequestApiDealHistory
import com.team.oleg.funder.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DealHistoryService {
    companion object {
        fun create(): RequestApiDealHistory {
            val gson = Gson()
            val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(RequestApiDealHistory::class.java)
        }
    }
}