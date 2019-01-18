package com.team.oleg.funder.Company.Request

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Data.Chat

interface RequestContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showBidder(bidder: List<Bidder>)

        fun showBidderDetail(bidderId: String)

        fun showNoBidder(active: Boolean)
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadBidder(forceUpdate: Boolean)

        fun openBidderDetail(requestedBidder: Bidder)

    }
}