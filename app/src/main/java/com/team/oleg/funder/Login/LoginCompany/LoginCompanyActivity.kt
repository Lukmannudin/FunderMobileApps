package com.team.oleg.funder.Login.LoginCompany

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.team.oleg.funder.company.CompanyActivity
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Data.Company
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_login_company.*
import kotlinx.android.synthetic.main.activity_login_eo.*
import org.jetbrains.anko.intentFor

class LoginCompanyActivity : AppCompatActivity(), LoginCompanyContract.View {

    override lateinit var presenter: LoginCompanyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources,R.id.logoFunderLogin,options)

        logoFunderLogin2.setImageBitmap(
            decodeSampledBitmapFromResource(resources, R.drawable.logo, 270, 270)
        )

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

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions

        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this@run)



            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeResource(res, resId, this)
        }
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}
