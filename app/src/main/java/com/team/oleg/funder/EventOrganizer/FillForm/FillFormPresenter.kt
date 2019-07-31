package com.team.oleg.funder.EventOrganizer.FillForm

import com.team.oleg.funder.apirequest.EventService
import com.team.oleg.funder.data.Event
import com.team.oleg.funder.service.ApiService
import com.team.oleg.funder.service.firebase.FirestoreService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FillFormPresenter(
    private val FillFormView: FillFormContract.View
) : FillFormContract.Presenter {

    private var disposable: Disposable? = null

    init {
        FillFormView.presenter = this
    }

    override fun addEvent(event: Event) {
        val bid = HashMap<String,String?>()

        bid["Event Name"] = event.eventName
        bid["Artist"] = event.eventSpeaker
        bid["date"] = event.eventDate
        bid["media_partner"] = event.eventMp
        bid["description"] = event.eventDesc
        bid["demand_fund"] = event.eventDana

        FirestoreService.onBiddingSponsor(bid)
        val service: EventService = ApiService.eventService
        disposable = service.setEvent(event)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    if (result.data == "1") {
                        successMessage()
                    } else {
//                        FillFormView.showFailedMessage("Failed")
                    }
                },
                { error ->
                    error.printStackTrace()
                    FillFormView.showMessageError(error.localizedMessage)

                }
            )
    }

    private fun successMessage() {
//        FillFormView.showFailedMessage("asd")
        FillFormView.showMessageSuccess("BERHASIL")
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun downloadFile() {
    }

    override fun uploadFile() {
    }

    override fun start() {
    }

    override fun destroy() {
        disposable?.dispose()
    }

}