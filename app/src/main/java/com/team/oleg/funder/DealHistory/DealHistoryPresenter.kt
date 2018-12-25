package com.team.oleg.funder.DealHistory

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestApiDealHistory
import com.team.oleg.funder.Model.DealHistory
import com.team.oleg.funder.Service.DealHistoryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DealHistoryPresenter(
    private val dealHistoryView: DealHistoryContract.View
) : DealHistoryContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        dealHistoryView.presenter = this
    }


    override fun start() {
        loadDealHistory(false)
    }

    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadDealHistory(forceUpdate: Boolean) {
        loadDealHistory(forceUpdate || firstLoad, true)
    }


    private fun loadDealHistory(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            dealHistoryView.setLoadingIndicator(true)
        }

//        if (forceUpdate){
//
//        }

        val service: RequestApiDealHistory = DealHistoryService.create()
        disposable = service.getDealHistory("1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processDealHistory(result.data)
                    dealHistoryView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
    }

    private fun processDealHistory(dealHistory: List<DealHistory>) {
        if (dealHistory.isEmpty()) {
            Log.i("cek", "isEmpty")
        } else {
            dealHistoryView.showDealHistory(dealHistory)
        }
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }


    override fun openDetailHistoryDetail(requestedDealHistory: DealHistory) {
        requestedDealHistory.eventId?.let {
            dealHistoryView.showDealHistoryDetailUi(it)
        }
    }


}