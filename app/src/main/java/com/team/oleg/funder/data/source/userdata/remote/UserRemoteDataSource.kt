package com.team.oleg.funder.data.source.userdata.remote


import com.team.oleg.funder.Response.RootResponse
import com.team.oleg.funder.apirequest.UserService
import com.team.oleg.funder.data.Company
import com.team.oleg.funder.data.User
import com.team.oleg.funder.data.source.userdata.UserDataSource
import com.team.oleg.funder.service.ApiService
import io.reactivex.Observable

class UserRemoteDataSource private constructor(): UserDataSource {

    lateinit var INSTANCE: UserRemoteDataSource

    val service: UserService = ApiService.userService

    override fun login(user: User): Observable<RootResponse<User>> {
        return service.login(user)
    }

    override fun loginCompany(company: Company): Observable<RootResponse<Company>> {
        return service.loginCompany(company)
    }

    companion object {
        private lateinit var INSTANCE: UserRemoteDataSource
        private var needNewInstance = true

        @JvmStatic fun getInstance(): UserRemoteDataSource{
            if (needNewInstance){
                INSTANCE = UserRemoteDataSource()
                needNewInstance = false
            }

            return INSTANCE
        }
    }

}