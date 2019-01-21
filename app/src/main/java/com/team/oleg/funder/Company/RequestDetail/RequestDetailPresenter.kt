package com.team.oleg.funder.Company.RequestDetail

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


class RequestDetailPresenter(
    private val eventId: String?,
    private val requestDetailView: RequestDetailContract.View
) : RequestDetailContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        requestDetailView.presenter = this
    }

    override fun start() {
       loadEvent(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }


    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadEvent(forceUpdate: Boolean) {
        loadEvent(forceUpdate || firstLoad, true)
    }

    private fun loadEvent(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            requestDetailView.setLoadingIndicator(true)
        }

//        if (forceUpdate) {
//        }

        val service: EventService = ApiService.eventService
        disposable = service.getEvent(eventId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processEvent(result.data)
                    requestDetailView.setLoadingIndicator(false)
                },
                { error ->
                    requestDetailView.setLoadingIndicator(false)
                    Log.i("ERROR",error.localizedMessage)
                }
            )
    }

    private fun processEvent(event: Event) {
        requestDetailView.showEvent(event)
    }

    override fun eventApproval(status: Boolean, bidderId: String?) {
        val service: EventService = ApiService.eventService
        var message = Utils.MESSAGE_EVENT_REJECTED
        var serviceMethod = service.setBidderRejected(bidderId)

        if (status){
            serviceMethod = service.setBidderAccepted(bidderId)
            message = Utils.MESSAGE_EVENT_ACCEPTED
        }

        disposable = serviceMethod
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    requestDetailView.showDialogMessage(message)
                },
                { error ->
                    Log.i("result event",error.localizedMessage)
                }
            )
    }


}