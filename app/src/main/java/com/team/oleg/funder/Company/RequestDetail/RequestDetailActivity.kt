package com.team.oleg.funder.Company.RequestDetail

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.team.oleg.funder.Company.CompanyActivity
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_request_detail.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor

class RequestDetailActivity : AppCompatActivity(), RequestDetailContract.View {


    override lateinit var presenter: RequestDetailContract.Presenter
    var bidderId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)
        val eventId = intent.getStringExtra(Utils.ID)
        bidderId = intent.getStringExtra(Utils.BIDDER_ID)
        presenter = RequestDetailPresenter(eventId, this)

        bReject.setOnClickListener {
            presenter.eventApproval(false, bidderId)
        }

        bAccept.setOnClickListener {
            presenter.eventApproval(true, bidderId)
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showEvent(event: Event) {
        dateEventEO.text = event.eventDate
        nameSpeakerEO.text = event.eventSpeaker
        nameMediaPartEO.text = event.eventMp
        descriptionFullEO.text = event.eventDesc
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun showDialogMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        view.titleFillFormSubmitted.text = message
        view.btnSubmitFillForm.setOnClickListener {
            startActivity(intentFor<CompanyActivity>())

        }
        builder.setView(view)
        builder.show()
    }
}
