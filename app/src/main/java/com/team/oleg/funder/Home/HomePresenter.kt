package com.team.oleg.funder.Home

import com.team.oleg.funder.Model.Sponsor

class HomePresenter(val auctionView: HomeContract.View): HomeContract.Presenter{
    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
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
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     * *
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadAuction(forceUpdate: Boolean, showLoadingUI: Boolean){
        if (showLoadingUI){
            auctionView.setLoadingIndicator(true)
        }

    }

    override fun openAuctionDetail(requestedAuction: Sponsor) {
    }

    override fun openTopFunderDetail(requestedTopFunder: TopFunder) {
    }


}