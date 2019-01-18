package com.team.oleg.funder.Company.Request

import com.team.oleg.funder.APIRequest.ChatService
import com.team.oleg.funder.APIRequest.DealHistoryService
import com.team.oleg.funder.Company.Chat.ChatCompanyContract
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Data.Chat
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RequestPresenter(
    private val companyId: String?,
    private val requestView: RequestContract.View
) : RequestContract.Presenter {


    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        requestView.presenter = this
    }


    override fun start() {
        loadBidder(false)
        requestView.showNoBidder(false)
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
            requestView.setLoadingIndicator(true)
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
                    requestView.setLoadingIndicator(false)
                },
                { error ->
                    requestView.showNoBidder(true)
                }
            )
    }

    private fun processChat(bidder: List<Bidder>) {
        if (bidder.isEmpty()) {
            requestView.showNoBidder(true)
        } else {
            requestView.showBidder(bidder)
        }
    }

    override fun openBidderDetail(requestedBidder: Bidder) {
        requestedBidder.bidderId?.let {
            requestView.showBidderDetail(it)
        }
    }




}