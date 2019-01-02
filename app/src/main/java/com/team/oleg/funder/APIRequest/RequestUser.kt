package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Model.User
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestUser {
    @POST("login/eo")
    fun login(@Body user: User):
            Observable<RootResponse<User>>
}