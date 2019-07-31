package com.team.oleg.funder.data

import android.content.Context
import com.team.oleg.funder.data.source.userdata.UserDataRepository
import com.team.oleg.funder.data.source.userdata.remote.UserRemoteDataSource

object UserDataInjection {
    fun provideRepository(context: Context): UserDataRepository {
        checkNotNull(context)
//        val database = FunderDatabase.getInstance(context)
        return UserDataRepository.getInstance(UserRemoteDataSource.getInstance())
    }
}