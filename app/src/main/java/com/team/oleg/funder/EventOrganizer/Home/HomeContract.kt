package com.team.oleg.funder.EventOrganizer.Home

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Sponsor

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTopFunder(topFunder: List<Sponsor>)

        fun showAuction(sponsor: List<Sponsor>)

        fun showAuctionDetailsUi(sponsor : Sponsor)

        fun showNoAuction()

        fun showUnreadChat(count: String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun loadSponsor(forceUpdate: Boolean)

        fun openSponsorDetail(requestedAuction: Sponsor)

        fun loadUnreadChat(userId:String)
    }
}

