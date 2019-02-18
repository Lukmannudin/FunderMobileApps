package com.team.oleg.funder.company

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_company.*

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        supportActionBar?.elevation = 0f
        vpCompanyMain.adapter = CompanyAdapter(supportFragmentManager)
        tbCompanyMain.setupWithViewPager(vpCompanyMain)
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

}
