package com.team.oleg.funder.EventOrganizer.ChangePassword

import android.util.Log
import com.team.oleg.funder.APIRequest.UserService
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChangePasswordPresenter(
    private val ChangePasswordView: ChangePasswordContract.View
) : ChangePasswordContract.Presenter {


    private var disposable: Disposable? = null

    init {
        ChangePasswordView.presenter = this
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun changePassword(userId: String, newPassword: String) {
        val password: HashMap<String, String> = hashMapOf()
        password["eo_password"] = newPassword
        val service: UserService = ApiService.userService
        disposable = service.changePassword(userId, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    ChangePasswordView.showMessage("Edited Account Success")
                },
                { error ->
                    //                    FillFormView.showMessageError(error.localizedMessage)
                    Log.i("cek", error.localizedMessage)
                }
            )
    }


    override fun start() {
    }

    override fun destroy() {
        disposable?.dispose()
    }

}