package com.team.oleg.funder.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Model.User
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_login_eo.*
import org.jetbrains.anko.intentFor

class LoginEOActivity : AppCompatActivity(),LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_eo)
        supportActionBar?.hide()


        loginCompany.setOnClickListener {
            startActivity(intentFor<LoginCompanyActivity>())
        }

        LoginSignupLink.setOnClickListener {
            startActivity(intentFor<SignUpActivity>())
        }


        val user = User()
        presenter = LoginPresenter(this)
        loginButton.setOnClickListener {
            user.eoEmail = edtUsername.text.toString()
            user.eoPassword = edtPassword.text.toString()
            presenter.loadUser(user)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
        val sharedPref = this.getPreferences(0) ?: return
        val USER_ID = sharedPref.getString(SharedPreferenceUtils.USER_ID, "null")
        if (USER_ID != "null"){
            startActivity(intentFor<MainActivity>())
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active){
            loginLoading.visibility = View.VISIBLE
        } else {
            loginLoading.visibility = View.GONE
        }
    }

    override fun showIsSuccessfull(user:User) {

        val sharedPref = this.getPreferences(0) ?: return
        with (sharedPref.edit()) {
            putString(SharedPreferenceUtils.USER_ID,user.eoId)
            putString(SharedPreferenceUtils.USER_NAME,user.eoName)
            putString(SharedPreferenceUtils.USER_EMAIL,user.eoEmail)
            putString(SharedPreferenceUtils.USER_POINT,user.eoPoint)
            putString(SharedPreferenceUtils.USER_PHOTO,user.eoPhoto)
            apply()
        }
        startActivity(intentFor<MainActivity>())
    }

    override fun showIsFailed(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
