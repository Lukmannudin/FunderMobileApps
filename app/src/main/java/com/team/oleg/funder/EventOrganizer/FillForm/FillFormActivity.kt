package com.team.oleg.funder.EventOrganizer.FillForm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_fill_form.*
import org.jetbrains.anko.intentFor

class FillFormActivity : AppCompatActivity(), FillFormContract.View {

    override lateinit var presenter: FillFormContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        val sponsorData =intent.getParcelableExtra<Sponsor>(Utils.INTENT_PARCELABLE)
        backFillForm.setOnClickListener {
            startActivity(intentFor<AuctionActivity>(
                Utils.INTENT_PARCELABLE to sponsorData
            ))
        }

        presenter = FillFormPresenter(this)


        presenter.addEvent(
            Event(
                "1",
                "1",
                "1",
                "test",
                "test",
                "test",
                "test",
                "test",
                null,
                "available"
            )
        )
    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessageError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessageSuccess(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
