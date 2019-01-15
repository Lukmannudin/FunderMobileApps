package com.team.oleg.funder.EventOrganizer.DealHistory

import android.util.Log
import com.team.oleg.funder.APIRequest.DealHistoryService
import com.team.oleg.funder.Data.DealHistory
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DealHistoryPresenter(
    private val userId:String?,
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

        val service: DealHistoryService = ApiService.dealHistoryService
        Log.i("cokodot",userId)
        disposable = service.getDealHistory(userId)
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
            dealHistoryView.showNoDealHistory()
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