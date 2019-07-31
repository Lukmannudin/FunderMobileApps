package com.team.oleg.funder.nlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.team.oleg.funder.R
import com.team.oleg.funder.data.User
import com.team.oleg.funder.databinding.ActivityLoginEoBinding
import com.team.oleg.funder.utils.obtainViewModel

class LoginActivity : AppCompatActivity(), LoginUserActionsListener {

    private lateinit var viewModel: LoginViewModel
    lateinit var loginEOBinding: ActivityLoginEoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginEOBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_eo)

    }

    override fun onLoginClick() {
        val user = User()
        user.eoEmail = "kuipui@gmail.com"
        user.eoPassword = "mamassiapaitu"
        viewModel.onClickLogin(user)
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)
}