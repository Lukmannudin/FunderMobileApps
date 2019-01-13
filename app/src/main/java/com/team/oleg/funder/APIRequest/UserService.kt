package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Company
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("login/eo")
    fun login(@Body user: User):
            Observable<RootResponse<User>>

    @POST("login/company")
    fun loginCompany(@Body company: Company):
            Observable<RootResponse<Company>>
}