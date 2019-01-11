package com.team.oleg.funder.EventOrganizer.DealHistory

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.DealHistory

interface DealHistoryContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showDealHistory(dealHistory: List<DealHistory>)

        fun showDealHistoryDetailUi(dealHistoryId: String)

        fun showNoDealHistory()
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadDealHistory(forceUpdate: Boolean)

        fun openDetailHistoryDetail(requestedDealHistory: DealHistory)
    }
}