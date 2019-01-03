package com.team.oleg.funder.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.Company.CompanyActivity
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import org.jetbrains.anko.intentFor

class LoginPresenter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val USER_TYPE = sharedPref.getString(SharedPreferenceUtils.USER_TYPE, SharedPreferenceUtils.USER_TYPE)

        if (USER_TYPE != SharedPreferenceUtils.EMPTY) {
            when (USER_TYPE) {
                SharedPreferenceUtils.USER_EO -> {
                    startActivity(intentFor<MainActivity>())
                }

                SharedPreferenceUtils.USER_COMPANY -> {
                    startActivity(intentFor<CompanyActivity>())
                }
                else -> {
                    startActivity(intentFor<LoginEOActivity>())
                }
            }

        }
    }
}