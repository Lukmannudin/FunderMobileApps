package com.team.oleg.funder.FillForm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Model.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_fill_form.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        val sponsorData =intent.getParcelableExtra<Sponsor>(Utils.INTENT_PARCELABLE)
        backFillForm.setOnClickListener {
            startActivity(intentFor<AuctionActivity>())
        }

    }
}
