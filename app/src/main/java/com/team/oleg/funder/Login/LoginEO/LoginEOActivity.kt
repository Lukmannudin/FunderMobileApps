package com.team.oleg.funder.Login.LoginEO

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.team.oleg.funder.Login.LoginCompany.LoginCompanyActivity
import com.team.oleg.funder.Login.SignUp.SignUpActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Login.ForgotPasswordActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.activity_login_eo.*
import org.jetbrains.anko.intentFor

class LoginEOActivity : AppCompatActivity(), LoginEOContract.View {

    override lateinit var presenter: LoginEOContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_eo)
        supportActionBar?.hide()

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources,R.id.logoFunderLogin,options)

        logoFunderLogin.setImageBitmap(
            decodeSampledBitmapFromResource(resources, R.drawable.logo, 270, 270)
        )

        loginCompany.setOnClickListener {
            startActivity(intentFor<LoginCompanyActivity>())
        }

        LoginSignupLink.setOnClickListener {
            startActivity(intentFor<SignUpActivity>())
        }

        forgotPasswordBtn.setOnClickListener {
            startActivity(intentFor<ForgotPasswordActivity>())
        }

        val user = User()
        presenter = LoginEOPresenter(this)
        signUpButton.setOnClickListener {
            user.eoEmail = edtUsername.text.toString()
            user.eoPassword = edtPassword.text.toString()
            presenter.loadUser(user)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loginLoading.visibility = View.VISIBLE
        } else {
            loginLoading.visibility = View.GONE
        }
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


    override fun showIsSuccessfull(user: User) {
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0)
        with(sharedPref.edit()) {
            putString(SharedPreferenceUtils.USER_ID, user.eoId)
            putString(SharedPreferenceUtils.USER_NAME, user.eoName)
            putString(SharedPreferenceUtils.USER_EMAIL, user.eoEmail)
            putString(SharedPreferenceUtils.USER_POINT, user.eoPoint)
            putString(SharedPreferenceUtils.USER_PHOTO, user.eoPhoto)
            putString(SharedPreferenceUtils.USER_TYPE, SharedPreferenceUtils.USER_EO)
            apply()
        }
        startActivity(intentFor<MainActivity>())
    }

    override fun showIsFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}
