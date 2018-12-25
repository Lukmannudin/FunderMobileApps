package com.team.oleg.funder.DealHistory

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.team.oleg.funder.Model.DealHistory
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.fragment_deal_history.*
import kotlinx.android.synthetic.main.fragment_deal_history.view.*
import kotlinx.android.synthetic.main.toolbar.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DealHistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DealHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainDealHistoryFragment : Fragment(), DealHistoryContract.View {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override lateinit var presenter: DealHistoryContract.Presenter
    private val dealHistoryList: MutableList<DealHistory> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Fragment", "MainDeal")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        presenter = DealHistoryPresenter(this)
    }

    private val itemListener: DealHistoryItemListener = object : DealHistoryItemListener {
        override fun onDealHistoryClick(clickedDealHistory: DealHistory) {
            presenter.openDetailHistoryDetail(clickedDealHistory)
        }
    }

    private lateinit var listAdapter: DealHistoryAdapter

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = DealHistoryAdapter(context, dealHistoryList, itemListener)
        rvDealHistory.adapter = listAdapter

        setupSearchView()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_deal_history, container, false)
        view.rvDealHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.dealHistorySwipeRefresh.setOnRefreshListener {
            presenter.loadDealHistory(false)
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainDealHistoryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }


    override fun setLoadingIndicator(active: Boolean) {
        dealHistorySwipeRefresh.isRefreshing = active
    }

    override fun showDealHistory(dealHistory: List<DealHistory>) {
        dealHistoryList.clear()
        dealHistoryList.addAll(dealHistory)
        listAdapter.notifyDataSetChanged()
    }

    override fun showDealHistoryDetailUi(dealHistoryId: String) {
    }

    override fun showNoDealHistory() {
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        actionSearch.clearFocus()
        actionSearch.setQuery("", false);
        actionSearch.isIconified = true
    }
    private fun setupSearchView() {
        val searchIconImage = actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_button)
        searchIconImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.icon_search) })

        val searchIconCloseImage =
            actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        searchIconCloseImage.setImageDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.ic_close_white_24dp
            )
        })
        actionSearch.setOnSearchClickListener {
            tbTitle.visibility = View.GONE
            actionSearch.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
        }

        val searchIconEditText = actionSearch.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchIconEditText.setTextColor(resources.getColor(R.color.white))

        actionSearch.setOnCloseListener {
            actionSearch.layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT
            tbTitle.visibility = View.VISIBLE
            false
        }
    }


    interface DealHistoryItemListener {
        fun onDealHistoryClick(clickedDealHistory: DealHistory)
    }


}
