package com.team.oleg.funder.data.source.userdata

import com.team.oleg.funder.Response.RootResponse
import com.team.oleg.funder.data.Company
import com.team.oleg.funder.data.User
import io.reactivex.Observable

class UserDataRepository(
    private val userRemoteDataSource: UserDataSource
) : UserDataSource {

    override fun login(user: User): Observable<RootResponse<User>> {
        return userRemoteDataSource.login(user)
    }

    override fun loginCompany(company: Company): Observable<RootResponse<Company>> {
        return userRemoteDataSource.loginCompany(company)
    }

    companion object {

        private var INSTANCE: UserDataRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        @JvmStatic
        fun getInstance(userRemoteDataSource: UserDataSource) =
            INSTANCE ?: synchronized(UserDataRepository::class.java) {
                INSTANCE ?: UserDataRepository(userRemoteDataSource)
                    .also { INSTANCE = it }
            }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}