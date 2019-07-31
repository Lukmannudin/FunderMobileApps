package com.team.oleg.funder.data.source.userdata

import com.team.oleg.funder.Response.RootResponse
import com.team.oleg.funder.data.Company
import com.team.oleg.funder.data.User
import io.reactivex.Observable

interface UserDataSource {

    fun login(user: User): Observable<RootResponse<User>>

    fun loginCompany(company: Company): Observable<RootResponse<Company>>

}