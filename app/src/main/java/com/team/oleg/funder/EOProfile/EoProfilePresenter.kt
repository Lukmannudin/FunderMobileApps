package com.team.oleg.funder.EOProfile

import android.util.Log
import com.team.oleg.funder.apirequest.UserService
import com.team.oleg.funder.data.Sponsor
import com.team.oleg.funder.data.User
import com.team.oleg.funder.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EoProfilePresenter(
    private val auctionView: EoProfileContract.View
) : EoProfileContract.Presenter {


    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
//        loadSponsor(false)
    }

    override fun destroy() {
        disposable?.dispose()
    }


    override fun loadUser(userId: String) {

        val service: UserService = ApiService.userService
        disposable = service.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("caku", "BERHASIL")
                    processUser(result.data)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )

    }

    private fun processUser(user: User){
        auctionView.showData(user)
    }
    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
        Log.i("result: ", "requestCode: $requestCode| resultCode:$resultCode")
    }


    override fun openSponsorDetail(requestedAuction: Sponsor) {
    }


}