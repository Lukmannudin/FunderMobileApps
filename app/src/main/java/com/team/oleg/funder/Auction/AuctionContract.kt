package com.team.oleg.funder.Auction

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.Response.SponsorResponse

interface AuctionContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showSponsor(sponsor: SponsorResponse)

        fun clickedInterested(sponsorId: String?, sponsor: Sponsor)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadSponsor(forceUpdate: Boolean)

    }
}