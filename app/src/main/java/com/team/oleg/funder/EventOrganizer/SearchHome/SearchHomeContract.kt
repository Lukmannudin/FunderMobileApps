package com.team.oleg.funder.EventOrganizer.SearchHome

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Sponsor

interface SearchHomeContract {
    interface View : BaseView<Presenter>{
        fun setLoadingIndicator(active : Boolean)
        fun showSponsor(sponsor: List<Sponsor>)
        fun showNoSponsor()
        fun showDetailSponsor(sponsor: Sponsor)
    }
    interface Presenter : BasePresenter {
        fun searchSponsor(keyword: String, showLoadingUI: Boolean)
        fun openSponsorDetail(clickedSponsor: Sponsor)
    }
}