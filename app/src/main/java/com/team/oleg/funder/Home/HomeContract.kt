package com.team.oleg.funder.Home

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Sponsor

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTopFunder(topFunder: List<Sponsor>)

        fun showAuction(sponsor: List<Sponsor>)

        fun showAuctionDetailsUi(auctionId : Int)

        fun showNoAuction()

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun loadTopFunder(forceUpdate: Boolean)

        fun loadAuction(forceUpdate: Boolean)

        fun openSponsorDetail(requestedAuction: Sponsor)
    }
}

