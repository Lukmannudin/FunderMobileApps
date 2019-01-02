package com.team.oleg.funder.Login

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Model.User

interface LoginContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showIsSuccessfull(user:User)

        fun showIsFailed(message: String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun loadUser(user: User)

    }
}

