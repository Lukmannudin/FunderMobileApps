package com.team.oleg.funder.EventOrganizer.DealHistory

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.team.oleg.funder.data.DealHistory
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.fragment_deal_history.*
import kotlinx.android.synthetic.main.fragment_deal_history.view.*
import kotlinx.android.synthetic.main.toolbar.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainDealHistoryFragment : androidx.fragment.app.Fragment(), DealHistoryContract.View {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override lateinit var presenter: DealHistoryContract.Presenter
    private val dealHistoryList: MutableList<DealHistory> = mutableListOf()
    private lateinit var listAdapter: DealHistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val sharedPref = this.activity?.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        presenter = DealHistoryPresenter(userId,this)
    }

    private val itemListener: DealHistoryItemListener = object :
        DealHistoryItemListener {
        override fun onDealHistoryClick(clickedDealHistory: DealHistory) {
            presenter.openDetailHistoryDetail(clickedDealHistory)
        }
    }


    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = DealHistoryAdapter(
            context,
            dealHistoryList,
            itemListener
        )
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
        view.rvDealHistory.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        view.dealHistorySwipeRefresh.setOnRefreshListener {
            presenter.loadDealHistory(false)
        }
        return view
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
        presenter.destroy()
    }

    private fun setupSearchView() {
        val searchIconImage = actionSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        searchIconImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.icon_search) })

        val searchIconCloseImage =
            actionSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
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

        val searchIconEditText = actionSearch.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
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
