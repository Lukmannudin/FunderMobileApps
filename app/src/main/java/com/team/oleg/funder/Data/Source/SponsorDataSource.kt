package com.team.oleg.funder.Data.Source

import com.team.oleg.funder.Data.Sponsor
import io.reactivex.Flowable;

interface SponsorDataSource {
    fun getTasks(): Flowable<List<Sponsor>>?

    fun saveSponsor(sponsor: Sponsor)

    fun clearCompletedSponsor()

    fun refreshSponsor()

    fun deleteAllSponsor()

    fun deleteSponsor(sponsorId: String)


}