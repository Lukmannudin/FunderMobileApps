package com.team.oleg.funder.apirequest

import com.team.oleg.funder.data.Company
import com.team.oleg.funder.data.Sponsor
import com.team.oleg.funder.Response.Response
import com.team.oleg.funder.Response.RootResponse
import com.team.oleg.funder.data.User
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
    fun getUser(@Path("userId") userId: String):
            Observable<Response<User>>

    @POST("userEO/edit/{userId}")
    fun changeUser(
        @Path("userId") userId: String?,
        @Body user: User
    ): Observable<Response<String>>

    @POST("userEO/changePass/{userId}")
    fun changePassword(
        @Path("userId") userId: String,
        @Body password: HashMap<String, String>
    ) : Observable<Response<String>>


    @POST("userEO/photo/{userId}")
    fun changeImageProfile(
        @Path("userId") userId: String?,
        @Body nameImage: HashMap<String, String?>
    ): Observable<Response<String>>
}