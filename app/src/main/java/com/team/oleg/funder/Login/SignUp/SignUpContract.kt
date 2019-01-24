package com.team.oleg.funder.Login.SignUp

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.User

interface SignUpContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun addUser(user: User)

        fun downloadFile()

        fun uploadFile()
    }
}

