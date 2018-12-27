package com.team.oleg.funder.Sponsor

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestApiSponsor
import com.team.oleg.funder.Auction.AuctionContract
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.Response.SponsorResponse
import com.team.oleg.funder.Service.SponsorService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AuctionPresenter(
    private val sponsorId: String,
    private val sponsorView: AuctionContract.View
) : AuctionContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        sponsorView.presenter = this
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
            sponsorView.setLoadingIndicator(true)
        }
//        if (forceUpdate){
//
//        }

        val service: RequestApiSponsor = SponsorService.create()
        disposable = service.getSponsorById(sponsorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("idIn",sponsorId)
                    processSponsor(result)
                    sponsorView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
        sponsorView.setLoadingIndicator(true)
        disposable = service.getSponsorTopFunder()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    sponsorView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)
    }


    private fun processSponsor(sponsor: SponsorResponse) {
        if (sponsor == null) {
            Log.i("cek", "isEmpty")
        } else {
            sponsorView.showSponsor(sponsor)
        }
    }


}