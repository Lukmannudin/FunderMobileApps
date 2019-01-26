package com.team.oleg.funder.EventOrganizer.ChangePassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Login.LoginPresenter
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ChangePasswordActivity : AppCompatActivity(), ChangePasswordContract.View {

    override lateinit var presenter: ChangePasswordContract.Presenter
    private var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        presenter = ChangePasswordPresenter(this)

        val data = intent.getParcelableExtra<User>(Utils.INTENT_PARCELABLE)
        user = data

        btnChangePassword.setOnClickListener {
            val currentPassword = currentPassword.text.toString()
            val newPassword = newPassword.text.toString()
            val confirmPassword = confirmNewPassword.text.toString()

            when {
                currentPassword != user.eoPassword -> toast("Current password is wrong")
                newPassword != confirmPassword -> toast("new password and confirm password not match")
                else -> {
                    user.eoPassword = newPassword
                    Log.i("cek",user.eoPassword)
                    user.eoId?.let { it1 -> presenter.changePassword(it1,newPassword) }
                }
            }
        }

    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showMessage(message: String) {
        toast("Change Password Successfully")
        startActivity(intentFor<LoginPresenter>())
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }
}
