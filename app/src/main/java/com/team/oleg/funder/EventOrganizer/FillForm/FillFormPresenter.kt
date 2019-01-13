package com.team.oleg.funder.EventOrganizer.FillForm

import com.team.oleg.funder.APIRequest.EventService
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Service.ApiService
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
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    if (result.data == "1"){
                        FillFormView.showMessageSuccess("Success")
                    } else {
                        FillFormView.showFailedMessage("Failed")
                    }
                },
                { error ->
                    FillFormView.showMessageError(error.localizedMessage)

                }
            )

    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun downloadFile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadFile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
    }

    override fun destroy() {
        disposable?.dispose()
    }

}