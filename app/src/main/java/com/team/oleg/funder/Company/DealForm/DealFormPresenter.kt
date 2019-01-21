package com.team.oleg.funder.Company.DealForm

import android.util.Log
import com.team.oleg.funder.APIRequest.EventService
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import android.R
import android.content.Context
import android.widget.Toast
import com.team.oleg.funder.Utils.Utils


class DealFormPresenter(
    private val requestDetailView: DealFormContract.View
) : DealFormContract.Presenter {

    private var disposable: Disposable? = null

    init {
        requestDetailView.presenter = this
    }

    override fun start() {
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }


    override fun destroy() {
        disposable?.dispose()
    }

    override fun sendTransfer(money: HashMap<String, String>, goods: HashMap<String, String>) {

//        val service: EventService = ApiService.eventService
//        disposable = service.getEvent(eventId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result ->
//                    processEvent(result.data)
//                    requestDetailView.setLoadingIndicator(false)
//                },
//                { error ->
//                    requestDetailView.setLoadingIndicator(false)
//                    Log.i("ERROR",error.localizedMessage)
//                }
//            )
    }



}