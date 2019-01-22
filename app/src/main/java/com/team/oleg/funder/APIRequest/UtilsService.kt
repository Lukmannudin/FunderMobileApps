package com.team.oleg.funder.APIRequest

import com.team.oleg.funder.Response.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface UtilsService {
    @POST("transfer/toEO/{bidderId}")
    fun sendTransfer(
        @Path("bidderId") bidderId: String,
        @Body transferData: HashMap<String, String>
    ): Observable<Response<String>>
}