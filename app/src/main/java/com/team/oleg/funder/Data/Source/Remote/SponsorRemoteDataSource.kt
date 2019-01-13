package com.team.oleg.funder.Data.Source.Remote

import com.team.oleg.funder.Data.Source.SponsorDataSource
import com.team.oleg.funder.Data.Sponsor
import io.reactivex.Flowable

class SponsorRemoteDataSource : SponsorDataSource {

//    private val INSTANCE: TasksRemoteDataSource? = null
    private lateinit var INSTANCE:SponsorRemoteDataSource

    private lateinit var SPONSORS_SERVICE_DATA: Map<String, Sponsor>

    init
    {
//        SPONSORS_SERVICE_DATA =
    }

    fun getInstance(): SponsorRemoteDataSource? {
        if (INSTANCE == null) {
             INSTANCE = SponsorRemoteDataSource()
        }
        return INSTANCE
    }
    override fun getTasks(): Flowable<List<Sponsor>>? {
        return null
    }

    override fun saveSponsor(sponsor: Sponsor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedSponsor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshSponsor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllSponsor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSponsor(sponsorId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}