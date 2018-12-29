package com.team.oleg.funder.FillForm

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Model.Sponsor

interface FillFormContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMessageError(message: String)

        fun showMessageSuccess(message: String)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun downloadFile()

        fun uploadFile()
    }
}

