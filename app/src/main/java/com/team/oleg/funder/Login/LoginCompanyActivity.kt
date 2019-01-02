package com.team.oleg.funder.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_login_company.*
import org.jetbrains.anko.intentFor

class LoginCompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)

        loginEventOrganizer.setOnClickListener {
            startActivity(intentFor<LoginEOActivity>())
        }
    }
}
