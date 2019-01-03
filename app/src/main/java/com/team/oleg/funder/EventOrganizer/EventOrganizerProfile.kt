package com.team.oleg.funder.EventOrganizer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_event_organizer_profile.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.jetbrains.anko.intentFor


class EventOrganizerProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_organizer_profile)
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
        tilEoVision.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvStringtilEoVisionCount.text = tilEoVision.editText?.text?.length.toString() + " / 120"
            }
        })

        logout.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN,0)
        if (sharedPref.getString(SharedPreferenceUtils.USER_ID,SharedPreferenceUtils.EMPTY)!= SharedPreferenceUtils.EMPTY){
            sharedPref.edit().clear().apply()
            startActivity(intentFor<LoginEOActivity>())
        }
    }

}
