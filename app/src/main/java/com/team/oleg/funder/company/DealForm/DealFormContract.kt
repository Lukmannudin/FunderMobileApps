package com.team.oleg.funder.company.DealForm

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.Bank
import com.team.oleg.funder.Data.Event

interface DealFormContract {
    interface View: BaseView<Presenter>{

        fun setLoadingIndicator(active: Boolean)

        fun showEvent(event: Event)

        fun showDialogMessage(message:String)

        fun setView(data: Bank)

    }

    interface Presenter: BasePresenter {



        fun getDataBank(eoId:String)

        fun result(requestCode:Int,resultCode:Int)

        fun sendTransfer(bidderId:String,funding:HashMap<String,String>)
    }
}