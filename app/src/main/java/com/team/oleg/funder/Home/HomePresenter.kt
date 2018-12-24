package com.team.oleg.funder.Home

import android.util.Log
import com.google.gson.Gson
import com.team.oleg.funder.APIRequest.APISponsors
import com.team.oleg.funder.APIRequest.ApiRepository
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Response.SponsorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePresenter(
    private val auctionView: HomeContract.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) : HomeContract.Presenter {


    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
        loadSponsor(false)
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

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(APISponsors.getSponsor()).await(),
                SponsorResponse::class.java
            )

            val data2 = gson.fromJson(
                apiRepository
                    .doRequest(APISponsors.getSponsor()).await(),
                SponsorResponse::class.java
            )
            if (showLoadingUI) {
                auctionView.setLoadingIndicator(false)
            }
            processTopFunder(data2.data)
            processAuction(data.data)

        }
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)
    }


    private fun processTopFunder(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
            Log.i("cek", "isEmpty")
        } else {
            auctionView.showTopFunder(sponsor)
        }
    }

    private fun processAuction(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
            Log.i("cek", "isEmpty")
        } else {
            auctionView.showAuction(sponsor)
        }
    }

    override fun openSponsorDetail(requestedAuction: Sponsor) {
        requestedAuction.sponsorId?.let {
            auctionView.showAuctionDetailsUi(it)
        }
    }


}