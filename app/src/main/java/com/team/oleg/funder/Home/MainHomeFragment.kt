package com.team.oleg.funder.Home

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import com.facebook.drawee.backends.pipeline.Fresco
import com.team.oleg.funder.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import kotlinx.android.synthetic.main.fragment_main_home.view.*
import kotlinx.android.synthetic.main.main_toolbar.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainHomeFragment : Fragment() {
    private val topFunderList: MutableList<TopFunder> = mutableListOf()
    private val auctionList: MutableList<Sponsor> = mutableListOf()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Fresco.initialize(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dummyDataTopFunder()
        ab_search.setOnCloseListener {
            ab_notification.visibility = View.VISIBLE
            ab_user_profile.visibility = View.VISIBLE
            ab_search.clearFocus()
            ab_search.setQuery("", false)
            false
        }
        ab_search.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        ab_search.setOnSearchClickListener {
            ab_notification.visibility = View.GONE
            ab_user_profile.visibility = View.GONE
            ab_search.maxWidth = Integer.MAX_VALUE
            val sc = ab_search.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            sc.setTextColor(resources.getColor(R.color.white))
//            LayoutMainHomeFragment.visibility = View.GONE
//            startActivity<SearchHomeActivity>()
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
        view.rvAuction.adapter = SponsorAdapter(context, topFunderList, auctionList)

        return view
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    private fun dummyDataTopFunder() {
        topFunderList.addAll(DummyTopFunder.getListData())
        auctionList.addAll(DummyAuction.getListData())
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu?.findItem(R.id.ab_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("ketik", "aya nu ngetik")
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })
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
        fun newInstance() = MainHomeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}


