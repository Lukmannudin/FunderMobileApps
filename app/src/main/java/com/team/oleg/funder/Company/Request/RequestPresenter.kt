package com.team.oleg.funder.Company.Request

import com.team.oleg.funder.APIRequest.DealHistoryService
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RequestPresenter(
    private val companyId: String?,
    private val requestDetailView: RequestContract.View
) : RequestContract.Presenter {


    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        requestDetailView.presenter = this
    }


    override fun start() {
        loadBidder(false)
        requestDetailView.showNoBidder(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }


    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadBidder(forceUpdate: Boolean) {
        loadBidder(forceUpdate || firstLoad, true)
    }

    private fun loadBidder(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            requestDetailView.setLoadingIndicator(true)
        }

//        if (forceUpdate) {
//        }

        val service: DealHistoryService = ApiService.dealHistoryService
        disposable = service.getBidder(companyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processChat(result.data)
                    requestDetailView.setLoadingIndicator(false)
                },
                { error ->
                    requestDetailView.setLoadingIndicator(false)
                    requestDetailView.showNoBidder(true)
                }
            )
    }

    private fun processChat(bidder: List<Bidder>) {
        if (bidder.isEmpty()) {
            requestDetailView.showNoBidder(true)
        } else {
            requestDetailView.showBidder(bidder)
        }
    }

    override fun openBidderDetail(requestedBidder: Bidder) {
        requestedBidder.eventId?.let { requestedBidder.bidderId?.let { it1 ->
            requestDetailView.showBidderDetail(it,
                it1
            )
        } }
    }
}