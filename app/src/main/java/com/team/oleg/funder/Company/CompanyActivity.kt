package com.team.oleg.funder.Company

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import org.jetbrains.anko.intentFor

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_profile, menu)
        val btnLogout = menu?.findItem(R.id.btnLogout)

        btnLogout?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                logout()
                return true
            }

        })
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
