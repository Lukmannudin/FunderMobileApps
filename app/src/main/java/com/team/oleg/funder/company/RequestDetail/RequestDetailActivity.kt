package com.team.oleg.funder.company.RequestDetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.company.CompanyActivity
import com.team.oleg.funder.data.Event
import com.team.oleg.funder.R
import com.team.oleg.funder.utils.Utils
import kotlinx.android.synthetic.main.activity_request_detail.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor
import java.text.NumberFormat
import java.util.*

class RequestDetailActivity : AppCompatActivity(), RequestDetailContract.View {


    override lateinit var presenter: RequestDetailContract.Presenter
    var bidderId: String? = null
    private val event = Event()

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
        nameEventEO.text = event.eventName
        dateEventEO.text = event.eventDate
        nameSpeakerEO.text = event.eventSpeaker
        nameMediaPartEO.text = event.eventMp
        descriptionFullEO.text = event.eventDesc
//        totalFundEO.text = event.eventDana

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        totalFundEO.text = numberFormat.format(event.eventDana?.toDouble())
        dlIconProposal.setOnClickListener {
            val storage = FirebaseStorage.getInstance()
            val storageRef: StorageReference? = storage.reference
            storageRef?.child("proposal/${event.eventProposal}")?.downloadUrl?.addOnSuccessListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, it)
                startActivity(browserIntent)
            }?.addOnFailureListener { Log.i("file", it.localizedMessage) }
        }
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
