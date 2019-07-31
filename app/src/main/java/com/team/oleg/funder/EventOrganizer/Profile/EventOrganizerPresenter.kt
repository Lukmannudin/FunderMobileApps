package com.team.oleg.funder.EventOrganizer.Profile

import android.util.Log
import com.team.oleg.funder.apirequest.UserService
import com.team.oleg.funder.data.Sponsor
import com.team.oleg.funder.data.User
import com.team.oleg.funder.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EventOrganizerPresenter(
    private val UserView: EventOrganizerContract.View
) : EventOrganizerContract.Presenter {



    private var disposable: Disposable? = null

    init {
        UserView.presenter = this
    }

    override fun getEO(userId: String) {
        Log.i("userId",userId)
        val service: UserService = ApiService.userService
        disposable = service.getUser(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    processData(result.data)
                    Log.i("cek",result.data.accountName)
                },
                { error ->
//                    Log.i("cek",error.localizedMessage)
                    //                    FillFormView.showMessageError(error.localizedMessage)
                }
            )
    }


    private fun processData(user: User) {
        UserView.showDataEO(user)
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun editUser(user: User) {
        val service: UserService = ApiService.userService
        disposable = service.changeUser(user.eoId,user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    UserView.showMessage("Edited Account Success")
                },
                { error ->
                    //                    FillFormView.showMessageError(error.localizedMessage)
                }
            )
    }

    override fun start() {
    }

    override fun changeProfleImage(userId: String?, newImage: String?) {
        val service: UserService = ApiService.userService
        val image: HashMap<String,String?> = hashMapOf()
        image["eo_photo"] = newImage
        disposable = service.changeImageProfile(userId,image)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    UserView.showMessage("Edited Account Success")
                },
                { error ->
                    //                    FillFormView.showMessageError(error.localizedMessage)
                    UserView.showMessage(error.localizedMessage)
                }
            )
    }

    override fun destroy() {
        disposable?.dispose()
    }

}