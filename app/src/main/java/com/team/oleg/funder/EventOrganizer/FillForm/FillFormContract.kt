package com.team.oleg.funder.EventOrganizer.FillForm

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.data.Event

interface FillFormContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMessageError(message: String)

        fun showMessageSuccess(message: String)

        fun showFailedMessage(message: String)
    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun addEvent(event: Event)

        fun downloadFile()

        fun uploadFile()
    }
}

