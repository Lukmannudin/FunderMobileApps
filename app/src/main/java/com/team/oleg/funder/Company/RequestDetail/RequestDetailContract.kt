package com.team.oleg.funder.Company.RequestDetail

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Event

interface RequestDetailContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showEvent(event: Event)

        fun showDialogMessage(message:String)
    }

    interface Presenter: BasePresenter {

        fun result(requestCode:Int,resultCode:Int)

        fun loadEvent(forceUpdate: Boolean)

        fun eventApproval(status:Boolean,bidderId:String?)
    }
}