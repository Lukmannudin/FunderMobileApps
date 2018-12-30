package com.team.oleg.funder.EventOrganizer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_event_organizer_profile.*


class EventOrganizerProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_organizer_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val content = SpannableString("View Track Record")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tvEOTrackRecordLink.text = content

        val content2 = SpannableString("Change Password")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tvChangePassword.text = content2

        tilEoVision.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvStringtilEoVisionCount.text = tilEoVision.editText?.text?.length.toString() + " / 120"
            }
        })
    }
}
