package com.team.oleg.funder.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Dummy.DummyAuction
import com.team.oleg.funder.R
import com.team.oleg.funder.SearchHome.SearchHomeActivity
import kotlinx.android.synthetic.main.fragment_main_home.view.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.jetbrains.anko.support.v4.intentFor


class MainHomeFragment : Fragment(),HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter


    private val topFunderList: MutableList<Sponsor> = mutableListOf()
    private val auctionList: MutableList<Sponsor> = mutableListOf()

    internal var itemListener: SponsorItemListener = object : SponsorItemListener {
        override fun onSponsorClick(clickedAuction: Sponsor) {
            presenter.openSponsorDetail(clickedAuction)
        }
    }

    private val listAdapter = SponsorAdapter(context, ArrayList(0),ArrayList(0), itemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        presenter.result(requestCode, resultCode)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dummyDataTopFunder()
        ab_search.setOnClickListener {
            startActivity(intentFor<SearchHomeActivity>())
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
        view.rvAuction.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.rvAuction.adapter = SponsorAdapter(context, topFunderList, auctionList, itemListener)
        return view
    }

    private fun dummyDataTopFunder() {
        topFunderList.addAll(DummyAuction.getListData())
        auctionList.addAll(DummyAuction.getListData())
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainHomeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        Log.i("load","onLoading")
    }

    override fun showTopFunder(topFunder: List<Sponsor>) {
        listAdapter.items = topFunder
    }

    override fun showAuction(sponsor: List<Sponsor>) {
        listAdapter.sponsor = sponsor
    }

    override fun showAuctionDetailsUi(auctionId: Int) {
        Log.i("id", "ID:$auctionId")
    }

    override fun showNoAuction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
            presenter.start()
    }


    interface SponsorItemListener {
        fun onSponsorClick(clickedAuction: Sponsor)
    }

}


