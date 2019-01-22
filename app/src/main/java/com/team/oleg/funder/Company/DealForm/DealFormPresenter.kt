package com.team.oleg.funder.Company.DealForm

import android.util.Log
import com.team.oleg.funder.APIRequest.UtilsService
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


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

    override fun sendTransfer(bidderId: String, funding: HashMap<String, String>) {

        val service: UtilsService = ApiService.utilService
        disposable = service.sendTransfer(bidderId,funding)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    //                    processEvent(result.data)
                    requestDetailView.setLoadingIndicator(false)
                },
                { error ->
                    requestDetailView.setLoadingIndicator(false)
                    Log.i("ERROR", error.localizedMessage)
                }
            )
    }


}