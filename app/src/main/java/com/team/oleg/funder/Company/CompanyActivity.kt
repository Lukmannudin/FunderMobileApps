package com.team.oleg.funder.Company

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_company.*
import org.jetbrains.anko.intentFor

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        supportActionBar?.elevation = 0f
        vpCompanyMain.adapter = CompanyAdapter(supportFragmentManager)
        tbCompanyMain.setupWithViewPager(vpCompanyMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_company, menu)
        val btnLogout = menu?.findItem(R.id.logout)

        btnLogout?.setOnMenuItemClickListener {
            logout()
            true
        }
        return true
    }

    private fun logout(){
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN,0)
        if (sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)!= SharedPreferenceUtils.EMPTY){
            sharedPref.edit().clear().apply()
            startActivity(intentFor<LoginEOActivity>())
        }
    }


}