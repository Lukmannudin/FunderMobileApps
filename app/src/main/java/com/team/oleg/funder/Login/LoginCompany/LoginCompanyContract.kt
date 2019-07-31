package com.team.oleg.funder.Login.LoginCompany

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.data.Company

interface LoginCompanyContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showIsSuccessfull(company: Company)

        fun showIsFailed(message: String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun loadUser(company: Company)

    }
}

