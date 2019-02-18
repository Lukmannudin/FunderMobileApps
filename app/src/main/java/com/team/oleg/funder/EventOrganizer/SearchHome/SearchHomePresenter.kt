package com.team.oleg.funder.EventOrganizer.SearchHome

import android.util.Log
import com.team.oleg.funder.APIRequest.SponsorService
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchHomePresenter(
    private val view: SearchHomeContract.View
) : SearchHomeContract.Presenter {

    private var disposable: Disposable? = null

    override fun start() {
    }

    override fun searchSponsor(keyword: String, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true)
        }

        val service: SponsorService = ApiService.sponsorService
        disposable = service.getSearchSponsor(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processData(result.data)
                },
                { error ->
                    view.showNoSponsor()
                }
            )
    }

    private fun processData(sponsor: List<Sponsor>) {
        view.showSponsor(sponsor)
    }

    override fun destroy() {
        disposable?.dispose()
    }

    override fun openSponsorDetail(clickedSponsor: Sponsor) {
        view.showDetailSponsor(clickedSponsor)
    }

}