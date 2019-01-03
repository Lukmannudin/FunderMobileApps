package com.team.oleg.funder.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.EventOrganizer.EventOrganizerProfile
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.SearchHome.SearchHomeActivity
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.fragment_main_home.*
import kotlinx.android.synthetic.main.fragment_main_home.view.*
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.main_toolbar.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity


class MainHomeFragment : Fragment(), HomeContract.View {

    override lateinit var presenter: HomeContract.Presenter

    private val topFunderList: MutableList<Sponsor> = mutableListOf()
    private val auctionList: MutableList<Sponsor> = mutableListOf()

    private var itemListener: SponsorItemListener = object : SponsorItemListener {
        override fun onSponsorClick(clickedAuction: Sponsor) {
            presenter.openSponsorDetail(clickedAuction)
        }
    }

    private lateinit var listAdapter: SponsorAdapter

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = HomePresenter(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = SponsorAdapter(context, topFunderList, auctionList, itemListener)
        rvAuction.adapter = listAdapter
        ab_search.setOnClickListener {
            startActivity(intentFor<SearchHomeActivity>())
        }
        ab_user_profile.setOnClickListener {
            startActivity(intentFor<EventOrganizerProfile>())
        }
    }

    @SuppressLint("RtlHardcoded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)
        view.rvAuction.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.tbUnreadChat.setOnClickListener {
            startActivity(intentFor<MainActivity>(
                Utils.NAME to Utils.TOCHAT
            ))
        }
        view.homeSwipeRefresh.setOnRefreshListener {
            presenter.loadSponsor(false)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainHomeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        homeSwipeRefresh.isRefreshing = active
    }

    override fun showTopFunder(topFunder: List<Sponsor>) {
        topFunderList.clear()
        topFunderList.addAll(topFunder)
        listAdapter.notifyDataSetChanged()
    }

    override fun showAuction(sponsor: List<Sponsor>) {
        auctionList.clear()
        auctionList.addAll(sponsor)
        listAdapter.notifyDataSetChanged()
    }

    override fun showAuctionDetailsUi(sponsor: Sponsor) {
        startActivity(intentFor<AuctionActivity>(
            Utils.INTENT_PARCELABLE to sponsor
        ))
    }

    private fun showToast(message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun showNoAuction() {
    }

    override fun showUnreadChat(count: String) {
        tbUnreadChat.text = count
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    interface SponsorItemListener {
        fun onSponsorClick(clickedAuction: Sponsor)
    }
}


