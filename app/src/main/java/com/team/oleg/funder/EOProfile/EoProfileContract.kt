package com.team.oleg.funder.EOProfile

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Data.User

interface EoProfileContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMessage(message:String)

        fun showData(user: User)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun loadUser(userId:String)

        fun openSponsorDetail(requestedAuction: Sponsor)
    }
}

