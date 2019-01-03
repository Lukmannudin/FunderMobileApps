package com.team.oleg.funder.Login.LoginCompany

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestUser
import com.team.oleg.funder.Model.Company
import com.team.oleg.funder.Service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginCompanyPresenter(
    private val loginView: LoginCompanyContract.View
) : LoginCompanyContract.Presenter {


    private var disposable: Disposable? = null

    init {
        loginView.presenter = this
    }

    override fun start() {

    }

    override fun result(requestCode: Int, resultCode: Int) {
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)

    }

    override fun loadUser(company: Company) {
        loadUser(company,true)
    }

    private fun loadUser(company: Company,showLoadingUI: Boolean) {
        if (showLoadingUI) {
            loginView.setLoadingIndicator(true)
        }
        val service: RequestUser = UserService.login()
        disposable = service.loginCompany(company)
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

    private fun processUser(company: Company){
        if (company.companyEmail == null){
            loginView.showIsFailed("Username or password wrong")
        } else {
            loginView.showIsSuccessfull(company)
        }
    }
    

    override fun destroy() {
        disposable?.dispose()
    }

}