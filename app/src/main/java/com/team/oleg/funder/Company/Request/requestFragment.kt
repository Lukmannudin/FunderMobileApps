package com.team.oleg.funder.Company.Request

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.oleg.funder.Company.RequestDetail.RequestDetailActivity
import com.team.oleg.funder.Company.RequestDetail.RequestDetailContract
import com.team.oleg.funder.Company.RequestDetail.RequestDetailPresenter
import com.team.oleg.funder.Data.Bidder

import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.fragment_request.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import org.jetbrains.anko.support.v4.intentFor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RequestFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RequestFragment : Fragment(), RequestContract.View {

    override lateinit var presenter: RequestContract.Presenter
    private val bidderList : MutableList<Bidder> = mutableListOf()
    private lateinit var listDetailAdapter: RequestAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }

        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = RequestPresenter(userId, this)
    }

    private val itemListener: RequestFragment.BidderItemListener = object :
        BidderItemListener {
        override fun onBidderClick(clicked: Bidder) {
            presenter.openBidderDetail(clicked)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.result(requestCode,resultCode)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listDetailAdapter = RequestAdapter(context,bidderList,itemListener)
        rvBidder.adapter = listDetailAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_request, container, false)
        view.rvBidder.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.bidderSwipeRefresh.setOnRefreshListener {
            presenter.loadBidder(false)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RequestFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun setLoadingIndicator(active: Boolean) {
        bidderSwipeRefresh.isRefreshing = active
    }

    override fun showBidder(bidder: List<Bidder>) {
        bidderList.clear()
        bidderList.addAll(bidder)
        listDetailAdapter.notifyDataSetChanged()
    }

    override fun showBidderDetail(eventId: String,bidderId:String) {
        startActivity(intentFor<RequestDetailActivity>(
            Utils.ID to eventId,
            Utils.BIDDER_ID to bidderId
        ))
    }

    override fun showNoBidder(active: Boolean) {
        bidderList.clear()
        listDetailAdapter.notifyDataSetChanged()
        if (active){
            no_bidder.visibility = View.VISIBLE
        } else {
            no_bidder.visibility = View.GONE
        }
    }

    interface BidderItemListener {
        fun onBidderClick(clicked: Bidder)
    }
}
