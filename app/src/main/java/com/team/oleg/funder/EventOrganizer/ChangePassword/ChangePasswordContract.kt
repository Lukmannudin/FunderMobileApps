package com.team.oleg.funder.EventOrganizer.ChangePassword

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.User

interface ChangePasswordContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMessage(message:String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun changePassword(userId:String,newPassword:String)

    }
}

