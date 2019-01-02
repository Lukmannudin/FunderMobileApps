package com.team.oleg.funder.Service

import com.google.gson.Gson
import com.team.oleg.funder.APIRequest.RequestUser
import com.team.oleg.funder.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    companion object {
        fun login(): RequestUser {
            val gson = Gson()
            val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(RequestUser::class.java)
        }
    }
}