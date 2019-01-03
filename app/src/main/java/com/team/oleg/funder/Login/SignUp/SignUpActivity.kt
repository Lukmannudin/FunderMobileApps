package com.team.oleg.funder.Login.SignUp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.R

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sign Up"
    }
}
