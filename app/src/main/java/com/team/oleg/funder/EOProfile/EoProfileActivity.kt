package com.team.oleg.funder.EOProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

class EoProfileActivity : AppCompatActivity() {

    //    override lateinit var presenter: EoProfileContract.Presenter
    private val user = User()
    private val sponsorList: MutableList<Sponsor> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eo_profile)
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference


        val data = intent.getParcelableExtra<User>(Utils.INTENT_PARCELABLE)


        storageRef?.child("userProfileImage/${data.eoPhoto}")
            ?.downloadUrl?.addOnSuccessListener {
            Glide.with(this).load(it).into(notificationCompanyImage)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }

        tvCompanyName.text = data.eoName
        eo_points.text = data.eoPoint
        eoVission.text = data.eoMission
        tvCompanyMissionValue.text = data.eoMission


        backEOProfileButton.setOnClickListener {
            onBackPressed()
        }
    }

    interface BidderItemListener {
        fun onBidClicked(clickedBid: Bidder)
    }

}
