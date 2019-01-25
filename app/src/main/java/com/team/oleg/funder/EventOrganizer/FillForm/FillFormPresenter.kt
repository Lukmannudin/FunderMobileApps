package com.team.oleg.funder.EventOrganizer.FillForm

import com.team.oleg.funder.APIRequest.EventService
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Service.ApiService
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