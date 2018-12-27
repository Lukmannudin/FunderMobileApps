package com.team.oleg.funder.Auction

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.Response.SponsorResponse
import com.team.oleg.funder.Sponsor.AuctionPresenter
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_auction.*
import org.jetbrains.anko.act

class AuctionActivity : AppCompatActivity(),AuctionContract.View {


    override lateinit var presenter: AuctionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auction)
        supportActionBar?.setHomeButtonEnabled(true)
        val auctionId = intent.getStringExtra(Utils.ID)
        Log.i("cekoprat","AuctionActivity$auctionId")
        presenter = AuctionPresenter(auctionId,this)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    override fun setLoadingIndicator(active: Boolean) {
        if (!active){
            sponsorDetailLoading.visibility = View.GONE
        } else {
            sponsorDetailLoading.visibility = View.VISIBLE
        }
    }

    override fun showSponsor(sponsor: SponsorResponse) {
        Glide.with(this).load(
            BuildConfig.BASE_URL
                    + "uploads/img/img_sponsor/${sponsor.data.sponsorImage}"
        )
            .into(ivAuction)
        tvTitleAuction.text = sponsor.data.sponsorName
        tvAuctionDesc.text = sponsor.data.sponsorDesc
        auctionRequirement.text = sponsor.data.sponsorReq
        btnSponsor.setOnClickListener {
            clickedInterested(sponsor.data.sponsorId)
        }
    }

    override fun clickedInterested(sponsorId: String?) {
        Toast.makeText(this,sponsorId,Toast.LENGTH_SHORT).show()
    }

}
