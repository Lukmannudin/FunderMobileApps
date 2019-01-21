package com.team.oleg.funder.Company.EOProfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_eo_profile.*

class EoProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eo_profile)

        backEOProfileButton.setOnClickListener {
            onBackPressed()
        }
    }
}
