package com.team.oleg.funder.EventOrganizer.Home

import android.util.Log
import com.team.oleg.funder.APIRequest.RequestApiChat
import com.team.oleg.funder.APIRequest.RequestApiSponsor
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.Service.ChatService
import com.team.oleg.funder.Service.SponsorService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(
    private val auctionView: HomeContract.View
) : HomeContract.Presenter {

    private var disposable: Disposable? = null
    private var firstLoad = true

    init {
        auctionView.presenter = this
    }

    override fun start() {
        loadSponsor(false)
        loadUnreadChat()
    }

    override fun destroy() {
        disposable?.dispose()
    }

    override fun loadSponsor(forceUpdate: Boolean) {
        loadSponsor(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadSponsor(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            auctionView.setLoadingIndicator(true)
        }
//        if (forceUpdate){
//
//        }

        val service: RequestApiSponsor = SponsorService.create()
        disposable = service.getSponsor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processAuction(result.data)
                    auctionView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
        auctionView.setLoadingIndicator(true)
        disposable = service.getSponsorTopFunder()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    processTopFunder(result.data)
                    auctionView.setLoadingIndicator(false)
                },
                { error ->
                    Log.e("Error", error.message)
                }
            )
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //If a task was successfully added, show
        Log.i("result: ", "requestCode: " + requestCode.toString() + "| resultCode:" + resultCode)
    }


    private fun processTopFunder(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
            auctionView.showNoAuction()
        } else {
            auctionView.showTopFunder(sponsor)
        }
    }

    private fun processAuction(sponsor: List<Sponsor>) {
        if (sponsor.isEmpty()) {
            auctionView.showNoAuction()
        } else {
            auctionView.showAuction(sponsor)
        }
    }

    override fun openSponsorDetail(requestedAuction: Sponsor) {
        auctionView.showAuctionDetailsUi(requestedAuction)
    }

    override fun loadUnreadChat() {
        val service: RequestApiChat = ChatService.create()
        disposable = service.getUnreadChatEO("1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("unread", result.data.unread)
                    processUnreadChat(result.data.unread)
                },
                { error ->
                    Log.i("unread:Error", error.toString())
                }
            )
    }

    private fun processUnreadChat(countUnread: String?) {
        countUnread?.let { auctionView.showUnreadChat(it) }
    }

}