package com.team.oleg.funder.Company.EOProfile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_eo_profile.*

class EoProfileActivity : AppCompatActivity(),EoProfileContract.View {

    override lateinit var presenter: EoProfileContract.Presenter
    private val user = User()
    private val sponsorList: MutableList<Sponsor> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eo_profile)
        val eoId = intent.getStringExtra(Utils.USER_ID)
        presenter = EoProfilePresenter("2",this)
        backEOProfileButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showMessage(message: String) {
    }

    override fun showSponsor(sponsor: List<Sponsor>) {
    }



}
