package com.team.oleg.funder.Login.SignUp

import android.util.Log
import com.team.oleg.funder.APIRequest.EventService
import com.team.oleg.funder.APIRequest.UserService
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignUpPresenter(
    private val signUpView: SignUpContract.View
) : SignUpContract.Presenter {


    private var disposable: Disposable? = null

    init {
        signUpView.presenter = this
    }

   override fun addUser(user: User) {
        val service: UserService = ApiService.userService
        disposable = service.createAccount(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    if (result.data == "1") {
                        signUpView.showMessage("success")
                        uploadFile()
                    } else {
                        signUpView.showMessage("error")
                    }
                },
                { error ->
                    signUpView.showMessage(error.localizedMessage)
                }
            )
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun downloadFile() {
    }

    override fun uploadFile() {

    }

    override fun start() {
    }

    override fun destroy() {
        disposable?.dispose()
    }

}