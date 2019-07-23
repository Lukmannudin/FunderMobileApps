package com.team.oleg.funder.Auction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.team.oleg.funder.BuildConfig
import com.team.oleg.funder.EventOrganizer.FillForm.FillFormActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.Response.SponsorResponse
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_auction.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.intentFor

class AuctionActivity : AppCompatActivity(),AuctionContract.View {

    override lateinit var presenter: AuctionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auction)

        val sponsorData = intent.getParcelableExtra<Sponsor>(Utils.INTENT_PARCELABLE)

        backButton.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }
        Glide.with(this).load(BuildConfig.BASE_URL
            + "uploads/photo/company_photo/" + sponsorData.companyPhoto
        ).into(tbImageIconLogo)

        tbNameTitle.text = sponsorData.companyName
        presenter = AuctionPresenter(""+sponsorData.sponsorId,this)
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
            clickedInterested(sponsor.data.sponsorId, sponsor.data)
        }
    }

    override fun clickedInterested(sponsorId: String?, sponsor: Sponsor) {
        startActivity(intentFor<FillFormActivity>(
            Utils.INTENT_PARCELABLE to sponsor
        ))
    }

    override fun showNoSponsor() {
    }

}
