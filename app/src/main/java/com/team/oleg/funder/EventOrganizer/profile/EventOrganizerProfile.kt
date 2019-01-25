package com.team.oleg.funder.EventOrganizer.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_event_organizer_profile.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class EventOrganizerProfile : AppCompatActivity(), EventOrganizerContract.View {

    private val userData = User()
    override fun setLoadingIndicator(active: Boolean) {
    }


    override lateinit var presenter: EventOrganizerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_organizer_profile)

        presenter = EventOrganizerPresenter(this)
        initView()

        val content = SpannableString("View Track Record")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tvEOTrackRecordLink.text = content

        val content2 = SpannableString("Change Password")
        content2.setSpan(UnderlineSpan(), 0, content2.length, 0)
        tvChangePassword.text = content2

        val content3 = SpannableString("Log Out")
        content3.setSpan(UnderlineSpan(), 0, content3.length, 0)
        logout.text = content3

        backButton.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }
        visiEO.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvStringtilEoVisionCount.text = visiEO.text.length.toString() + " / 120"
            }

        })

        saveChangesEO.setOnClickListener {
            userData.accountName = accountNameEO.text.toString()
            userData.bankName = bankNameEO.text.toString()
            userData.accountRek = accountNumberEO.text.toString()
            userData.eoVision = visiEO.text.toString()
            userData.eoMission = misiEO.text.toString()
            presenter.editUser(userData)
        }
        logout.setOnClickListener {
            logout()
        }
    }

    private fun initView() {


        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        val username = sharedPref.getString(SharedPreferenceUtils.USER_NAME, SharedPreferenceUtils.EMPTY)
        val userEmail = sharedPref.getString(SharedPreferenceUtils.USER_EMAIL, SharedPreferenceUtils.EMPTY)
        val userPoint = sharedPref.getString(SharedPreferenceUtils.USER_POINT, SharedPreferenceUtils.EMPTY)
        val userPhoto = sharedPref.getString(SharedPreferenceUtils.USER_PHOTO, SharedPreferenceUtils.EMPTY)

        tvEOName.text = username
        tvEOEmail.text = userEmail
        tvEOPoints.text = userPoint
        presenter.getEO(userId!!)

    }

    override fun showDataEO(user: User) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference

        accountNameEO.setText(user.accountName)
        bankNameEO.setText(user.bankName)
        accountNumberEO.setText(user.accountRek)
        visiEO.setText(user.eoVision)
        misiEO.setText(user.eoMission)
        userData.eoId = user.eoId
        userData.eoEmail = user.eoEmail
        userData.eoPassword = user.eoPassword
        userData.eoName = user.eoName
        userData.eoPoint = user.eoPoint
        userData.eoPhoto = user.eoPhoto

        storageRef?.child("userProfileImage/${user.eoPhoto}")?.downloadUrl?.addOnSuccessListener {
            Glide.with(this).load(it).into(EOImage)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }


    }

    override fun showMessage(message: String) {
        toast(message)
        startActivity(intentFor<EventOrganizerProfile>())
    }


    private fun logout() {
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0)
        if (sharedPref.getString(
                SharedPreferenceUtils.USER_ID,
                SharedPreferenceUtils.EMPTY
            ) != SharedPreferenceUtils.EMPTY
        ) {
            sharedPref.edit().clear().apply()
            startActivity(intentFor<LoginEOActivity>())
        }
    }

}
