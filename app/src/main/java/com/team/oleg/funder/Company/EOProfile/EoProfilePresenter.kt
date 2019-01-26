package com.team.oleg.funder.Company.EOProfile

import android.util.Log
import com.team.oleg.funder.APIRequest.ChatService
import com.team.oleg.funder.APIRequest.SponsorService
import com.team.oleg.funder.APIRequest.UserService
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.EventOrganizer.Home.HomeContract
import com.team.oleg.funder.Service.ApiService
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EoProfilePresenter(
    private val userId:String,
    private val auctionView: EoProfileContract.View
) : EoProfileContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
        loadSponsor(false)
    }

    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadSponsor(forceUpdate: Boolean) {
        loadSponsor(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadSponsor(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            auctionView.setLoadingIndicator(true)
        }
//        if (forceUpdate){
//
//        }

//        val service: RequestApiSponsor = SponsorService.create()
        val service: UserService = ApiService.userService
        disposable = service.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("caku","BERHASIL")
//                    processAuction(result.data)
//                    auctionView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
        Log.i("result: ", "requestCode: $requestCode| resultCode:$resultCode")
    }


    private fun processTopFunder(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
        } else {
        }
    }

    private fun processAuction(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
        } else {
        }
    }

    override fun openSponsorDetail(requestedAuction: Sponsor) {
    }



}