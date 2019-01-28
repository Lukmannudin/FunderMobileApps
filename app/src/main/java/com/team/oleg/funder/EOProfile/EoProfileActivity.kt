package com.team.oleg.funder.EOProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Data.Bidder
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_eo_profile.*

class EoProfileActivity : AppCompatActivity(),EoProfileContract.View {



    override lateinit var presenter: EoProfileContract.Presenter
    private val user = User()
    private val sponsorList: MutableList<Sponsor> = mutableListOf()
    private lateinit var listAdapter: EoProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eo_profile)
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference


        val data = intent.getParcelableExtra<User>(Utils.INTENT_PARCELABLE)
        trackRecordEO.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        presenter = EoProfilePresenter(this)
        if (data != null){
            listAdapter = EoProfileAdapter(this, data.deal,itemListener)
            trackRecordEO.adapter = listAdapter
            storageRef?.child("userProfileImage/${data.eoPhoto}")
                ?.downloadUrl?.addOnSuccessListener {
                Glide.with(this).load(it).into(notificationCompanyImage)
            }?.addOnFailureListener { Log.i("file", it.localizedMessage) }

            tvCompanyName.text = data.eoName
            eo_points.text = data.eoPoint
            eoVission.text = data.eoMission
            tvCompanyMissionValue.text = data.eoMission
        } else {
            val eoId = intent.getStringExtra(Utils.ID)
            presenter.loadUser(eoId)
        }

        backEOProfileButton.setOnClickListener {
            onBackPressed()
        }
    }

    private var itemListener: EoProfileActivity.BidderItemListener = object :
        EoProfileActivity.BidderItemListener {
        override fun onBidClicked(clickedBid: Bidder) {
        }
    }

    interface BidderItemListener {
        fun onBidClicked(clickedBid: Bidder)
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showMessage(message: String) {
    }

    override fun showData(user: User) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference
        listAdapter = EoProfileAdapter(this, user.deal,itemListener)
        trackRecordEO.adapter = listAdapter
        storageRef?.child("userProfileImage/${user.eoPhoto}")
            ?.downloadUrl?.addOnSuccessListener {
            Glide.with(this).load(it).into(notificationCompanyImage)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }

        tvCompanyName.text = user.eoName
        eo_points.text = user.eoPoint
        eoVission.text = user.eoMission
        tvCompanyMissionValue.text = user.eoMission
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }
}
