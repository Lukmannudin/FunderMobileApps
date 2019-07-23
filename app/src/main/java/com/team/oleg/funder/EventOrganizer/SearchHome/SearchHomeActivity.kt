package com.team.oleg.funder.EventOrganizer.SearchHome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.search_home.*
import org.jetbrains.anko.intentFor

class SearchHomeActivity : AppCompatActivity(), SearchHomeContract.View {

    private val sponsorList: MutableList<Sponsor> = mutableListOf()

    private lateinit var adapter: SearchHomeAdapter

    override lateinit var presenter: SearchHomeContract.Presenter

    private val itemListener: SponsorClickListener = object : SponsorClickListener {
        override fun onSponsorClick(clicked: Sponsor) {
            presenter.openSponsorDetail(clicked)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_home)

        closeButtonSearchHome.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }

        presenter = SearchHomePresenter(this)

        edtSearchMainHome.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.searchSponsor(s.toString(), true)
            }

        })

        adapter = SearchHomeAdapter(this, sponsorList, itemListener)
        rvSearchHome.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        rvSearchHome.adapter = adapter

    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showSponsor(sponsor: List<Sponsor>) {
        sponsorList.clear()
        sponsorList.addAll(sponsor)
        adapter.notifyDataSetChanged()
        Log.i("kepanggil", sponsorList.size.toString())

    }

    override fun showNoSponsor() {
    }

    override fun showDetailSponsor(sponsor: Sponsor) {
        startActivity(
            intentFor<AuctionActivity>(
                Utils.INTENT_PARCELABLE to sponsor
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    interface SponsorClickListener {
        fun onSponsorClick(clicked: Sponsor)
    }
}