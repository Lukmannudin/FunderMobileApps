package com.team.oleg.funder.Login.LoginCompany

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.team.oleg.funder.Company.CompanyActivity
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Data.Company
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_login_company.*
import org.jetbrains.anko.intentFor

class LoginCompanyActivity : AppCompatActivity(), LoginCompanyContract.View {

    override lateinit var presenter: LoginCompanyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)
        presenter = LoginCompanyPresenter(this)


        loginEventOrganizer.setOnClickListener {
            startActivity(intentFor<LoginEOActivity>())
        }

        val user = Company()
        loginButtonCompany.setOnClickListener {
            user.companyEmail = edtUsernameCompany.text.toString()
            user.companyPassword = edtPasswordCompany.text.toString()
            presenter.loadUser(user)
        }

    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loginLoadingCompany.visibility = View.VISIBLE
        } else {
            loginLoadingCompany.visibility = View.GONE
        }
    }

    override fun showIsFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showIsSuccessfull(company: Company) {
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0)
        with(sharedPref.edit()) {
            putString(SharedPreferenceUtils.USER_ID, company.companyId)
            putString(SharedPreferenceUtils.USER_NAME, company.companyName)
            putString(SharedPreferenceUtils.USER_PHOTO, company.companyPhoto)
            putString(SharedPreferenceUtils.USER_TYPE, SharedPreferenceUtils.USER_COMPANY)
            apply()
        }
        startActivity(intentFor<CompanyActivity>())
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}
