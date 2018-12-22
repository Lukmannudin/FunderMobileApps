package com.team.oleg.funder.Home

import android.util.Log
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Dummy.DummyAuction

class HomePresenter(val auctionView: HomeContract.View) : HomeContract.Presenter {


    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
        loadTopFunder(false)
        loadAuction(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
    }


    override fun loadAuction(forceUpdate: Boolean) {
        loadAuction(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    override fun loadTopFunder(forceUpdate: Boolean) {
        loadTopFunder(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     * *
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadAuction(forceUpdate: Boolean, showLoadingUI: Boolean) {
//        if (showLoadingUI){
//            auctionView.setLoadingIndicator(true)
//        }
//        if (forceUpdate){
//
//        }
        val auctionToShow = ArrayList<Sponsor>()
        auctionToShow.addAll(DummyAuction.getListData())
        if (showLoadingUI) {
            auctionView.setLoadingIndicator(false)
        }
        processTask(auctionToShow)
    }

    private fun loadTopFunder(forceUpdate: Boolean, showLoadingUI: Boolean) {
//        if (showLoadingUI){
//            auctionView.setLoadingIndicator(true)
//        }
//        if (forceUpdate){
//
//        }
        val topFunderToShow = ArrayList<Sponsor>()
        topFunderToShow.addAll(DummyAuction.getListData())
        if (showLoadingUI) {
            auctionView.setLoadingIndicator(false)
        }
        processTask(topFunderToShow)
    }



    private fun processTask(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
            Log.i("cek", "isEmpty")
        } else {
            auctionView.showAuction(sponsor)
        }
    }

    override fun openSponsorDetail(requestedAuction: Sponsor) {
        Log.i("cek","cek")
        auctionView.showAuctionDetailsUi(requestedAuction.sponsorId)
    }


}