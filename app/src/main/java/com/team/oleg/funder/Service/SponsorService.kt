package com.team.oleg.funder.Service

import com.google.gson.Gson
import com.team.oleg.funder.APIRequest.RequestApiSponsor
import com.team.oleg.funder.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class SponsorService {
    companion object {
        fun create(): RequestApiSponsor {
            val gson = Gson()
            val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(RequestApiSponsor::class.java)
        }
    }
}