package com.team.oleg.funder.company.DealForm

import android.util.Log
import com.team.oleg.funder.APIRequest.UtilsService
import com.team.oleg.funder.Data.Bank
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

    override fun getDataBank(eoId: String) {
        val service: UtilsService = ApiService.utilService
        disposable = service.getData(eoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    //                    processEvent(result.data)
                    proccessData(result.data)
                },
                { error ->
                    Log.i("capcus",error.localizedMessage)

                }
            )
    }

    override fun sendTransfer(bidderId: String, funding: HashMap<String, String>) {
        val service: UtilsService = ApiService.utilService
        disposable = service.sendTransfer(bidderId,funding)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    //                    processEvent(result.data)
                    Log.i("transfer",result.data)
                    requestDetailView.setLoadingIndicator(false)
                    requestDetailView.showDialogMessage("Thank you, your transfer successfully sending to this event")
                },
                { error ->
                    requestDetailView.setLoadingIndicator(false)
                    Log.i("ERROR", error.localizedMessage)
                    requestDetailView.showDialogMessage("Thank you, your transfer successfully sending to this event")
                }
            )
    }

    private fun proccessData(data:Bank){
        requestDetailView.setView(data)
    }

}