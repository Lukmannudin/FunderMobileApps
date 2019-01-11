package com.team.oleg.funder.EventOrganizer.FillForm

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestApiEvent
import com.team.oleg.funder.Model.Event
import com.team.oleg.funder.Service.EventService
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
        val service: RequestApiEvent = EventService.create()
        disposable = service.setEvent(event)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.i("cek","BERHASIL")
                },
                { error ->
                    Log.i("cek","GAGAL: ${error.localizedMessage}")

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}