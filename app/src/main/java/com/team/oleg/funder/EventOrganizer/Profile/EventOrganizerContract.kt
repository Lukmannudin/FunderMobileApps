package com.team.oleg.funder.EventOrganizer.Profile

import com.team.oleg.funder.BasePresenter
import com.team.oleg.funder.BaseView
import com.team.oleg.funder.Data.User

interface EventOrganizerContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showDataEO(user:User)

        fun showMessage(message:String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int,resultCode: Int)

        fun getEO(userId:String)

        fun changeProfleImage(userId: String?,newImage:String?)

        fun editUser(user:User)
    }
}

