package com.team.oleg.funder.Company.DealForm

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Data.Event

interface DealFormContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showEvent(event: Event)

        fun showDialogMessage(message:String)
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun sendTransfer(money:HashMap<String,String>,goods:HashMap<String,String>)
    }
}