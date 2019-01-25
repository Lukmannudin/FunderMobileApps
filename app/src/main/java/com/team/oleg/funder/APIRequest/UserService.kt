package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Data.Company
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Response.Response
import com.team.oleg.funder.Response.RootResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("login/eo")
    fun login(@Body user: User):
            Observable<RootResponse<User>>

    @POST("login/company")
    fun loginCompany(@Body company: Company):
            Observable<RootResponse<Company>>

    @POST("userEO/register")
    fun createAccount(@Body user: User):
            Observable<Response<String>>

    @GET("userEO/{userId}")
    fun getUser(@Path("userId")userId:String):
            Observable<Response<User>>

    @POST("userEO/{userId}")
    fun changeUser(
        @Path("userId") userId: String?,
        @Body user: User):
            Observable<Response<String>>


}