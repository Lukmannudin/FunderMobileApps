package com.team.oleg.funder.Login

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestUser
import com.team.oleg.funder.Model.User
import com.team.oleg.funder.Service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(
    private val loginView: LoginContract.View
) : LoginContract.Presenter {

    private var disposable: Disposable? = null

    init {
        loginView.presenter = this
    }


    override fun start() {

    }

    override fun result(requestCode: Int, resultCode: Int) {
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)

    }

    override fun loadUser(user: User) {
        loadUser(user,true)
    }

    private fun loadUser(user:User,showLoadingUI: Boolean) {
        if (showLoadingUI) {
            loginView.setLoadingIndicator(true)
        }
        val service: RequestUser = UserService.login()
        disposable = service.login(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                   processUser(result.data[0])

                },
                { error ->
                    loginView.showIsFailed("username or password is wrong")
                }
            )
        if (showLoadingUI) {
            loginView.setLoadingIndicator(false)
        }
    }

    private fun processUser(user:User){
        if (user.eoEmail == null){
            loginView.showIsFailed("Username or password wrong")
        } else {
            loginView.showIsSuccessfull(user)
        }
    }


    override fun destroy() {
        disposable?.dispose()
    }

}