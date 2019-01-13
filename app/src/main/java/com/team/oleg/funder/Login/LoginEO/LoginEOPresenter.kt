package com.team.oleg.funder.Login.LoginEO

import android.util.Log
import com.team.oleg.funder.APIRequest.UserService
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginEOPresenter(
    private val loginEOView: LoginEOContract.View
) : LoginEOContract.Presenter {


    private var disposable: Disposable? = null

    init {
        loginEOView.presenter = this
    }


    override fun start() {

    }

    override fun result(requestCode: Int, resultCode: Int) {
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)

    }

    override fun loadUser(user: User) {
        loadUser(user,true)
    }

    private fun loadUser(user: User, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            loginEOView.setLoadingIndicator(true)
        }
        val service: UserService = ApiService.userService
        disposable = service.login(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                   processUser(result.data[0])

                },
                { error ->
                    loginEOView.showIsFailed("username or password is wrong")
                }
            )
        if (showLoadingUI) {
            loginEOView.setLoadingIndicator(false)
        }
    }

    private fun processUser(user: User){
        if (user.eoEmail == null){
            loginEOView.showIsFailed("Username or password wrong")
        } else {
            loginEOView.showIsSuccessfull(user)
        }
    }


    override fun destroy() {
        disposable?.dispose()
    }

}