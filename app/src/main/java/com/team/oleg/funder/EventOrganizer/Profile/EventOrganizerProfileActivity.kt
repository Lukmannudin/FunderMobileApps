package com.team.oleg.funder.EventOrganizer.Profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.EOProfile.EoProfileActivity
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.EventOrganizer.ChangePassword.ChangePasswordActivity
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_event_organizer_profile.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class EventOrganizerProfileActivity : AppCompatActivity(), EventOrganizerContract.View {
    private var filePath: Uri? = null
    private var fileNameImage: String? = null
    private val PICK_IMAGE_REQUEST = 72
    private val userData = User()
    override fun setLoadingIndicator(active: Boolean) {
    }


    override lateinit var presenter: EventOrganizerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.team.oleg.funder.R.layout.activity_event_organizer_profile)

        presenter = EventOrganizerPresenter(this)
        initView()

        val content = SpannableString("View Track Record")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tvEOTrackRecordLink.text = content

        val content2 = SpannableString("Change Password")
        content2.setSpan(UnderlineSpan(), 0, content2.length, 0)
        tvChangePassword.text = content2

        val content3 = SpannableString("Log Out")
        content3.setSpan(UnderlineSpan(), 0, content3.length, 0)
        logout.text = content3

        backButton.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }
        visiEO.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvStringtilEoVisionCount.text = visiEO.text.length.toString() + " / 120"
            }
        })

        saveChangesEO.setOnClickListener {
            userData.accountName = accountNameEO.text.toString()
            userData.bankName = bankNameEO.text.toString()
            userData.accountRek = accountNumberEO.text.toString()
            userData.eoVision = visiEO.text.toString()
            userData.eoMission = misiEO.text.toString()
            presenter.editUser(userData)
        }

        imageEOUplad.setOnClickListener {
            chooseFile()
        }
        logout.setOnClickListener {
            logout()
        }


    }

    private fun initView() {
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val userId = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        val username = sharedPref.getString(SharedPreferenceUtils.USER_NAME, SharedPreferenceUtils.EMPTY)
        val userEmail = sharedPref.getString(SharedPreferenceUtils.USER_EMAIL, SharedPreferenceUtils.EMPTY)
        val userPoint = sharedPref.getString(SharedPreferenceUtils.USER_POINT, SharedPreferenceUtils.EMPTY)
        val userPhoto = sharedPref.getString(SharedPreferenceUtils.USER_PHOTO, SharedPreferenceUtils.EMPTY)

        tvEOName.text = username
        tvEOEmail.text = userEmail
        tvEOPoints.text = userPoint
        presenter.getEO(userId!!)

    }

    override fun showDataEO(user: User) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference

        accountNameEO.setText(user.accountName)
        bankNameEO.setText(user.bankName)
        accountNumberEO.setText(user.accountRek)
        visiEO.setText(user.eoVision)
        misiEO.setText(user.eoMission)
        userData.eoId = user.eoId
        userData.eoEmail = user.eoEmail
        userData.eoPassword = user.eoPassword
        userData.eoName = user.eoName
        userData.eoPoint = user.eoPoint
        userData.eoPhoto = user.eoPhoto
        userData.deal = user.deal

        storageRef?.child("userProfileImage/${user.eoPhoto}")?.downloadUrl?.addOnSuccessListener {
            Glide.with(this).load(it).into(EOImage)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }


        tvChangePassword.setOnClickListener {
            startActivity(
                intentFor<ChangePasswordActivity>(
                    Utils.INTENT_PARCELABLE to user
                )
            )
        }

        tvEOTrackRecordLink.setOnClickListener {
            startActivity(
                intentFor<EoProfileActivity>(
                    Utils.INTENT_PARCELABLE to user
                )
            )
        }

    }

    override fun showMessage(message: String) {
        toast(message)
        startActivity(intentFor<EventOrganizerProfileActivity>())
    }


    private fun logout() {
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0)
        if (sharedPref.getString(
                SharedPreferenceUtils.USER_ID,
                SharedPreferenceUtils.EMPTY
            ) != SharedPreferenceUtils.EMPTY
        ) {
            sharedPref.edit().clear().apply()
            startActivity(intentFor<LoginEOActivity>())
        }
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        val storageReference: StorageReference? = storage.reference
        Log.i("filenameImage", fileNameImage)

        Log.i("cacing",userData.eoPhoto)
        val desertRef = storageReference?.child("userProfileImage/${userData.eoPhoto}")

        desertRef?.delete()?.addOnSuccessListener {
            if (filePath != null) {
                Log.i("cek", filePath.toString())
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Updating...")
                progressDialog.show()
                val ref = storageReference.child("userProfileImage/$fileNameImage")
                ref.putFile(filePath!!).addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
                    presenter.changeProfleImage(userData.eoId, fileNameImage)
                }.addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }.addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Updated " + progress.toInt() + "%")
                }
            }
        }?.addOnFailureListener {
            Log.i("error",it.toString())
            toast("sorry there was an error on our server")
        }
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            data.data?.let { returnUri ->
                contentResolver.query(returnUri, null, null, null, null)
            }?.use { cursor ->
                cursor.moveToFirst()
                fileNameImage = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                uploadImage()
            }
        }
    }
}

