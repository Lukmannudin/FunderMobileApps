package com.team.oleg.funder.Login.SignUp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Data.User
import com.team.oleg.funder.Login.LoginPresenter
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_login_eo.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    private var filePath: Uri? = null
    private var fileNameImage: String? = null
    private val PICK_IMAGE_REQUEST = 71
    private val user = User()

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override lateinit var presenter: SignUpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sign Up"
        presenter = SignUpPresenter(this)
        passwordStatus.visibility = View.GONE
        signUpButton2.isEnabled = false

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources,R.id.logoFunderLogin,options)

        logoFunderLogin3.setImageBitmap(
            decodeSampledBitmapFromResource(resources, R.drawable.logo, 270, 270)
        )

        passwordRetype.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                passwordStatus.visibility = View.VISIBLE
                if (password.text.toString() == s.toString()) {
                    passwordStatus.text = "Password match"
                    signUpButton.isEnabled = true
                } else {
                    passwordStatus.text = "Password incorrect"
                    signUpButton.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btnUploadImageSignUp.setOnClickListener {
            chooseFile()
        }


        visi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvStringtilEoVisionCount.text = s?.length.toString() + " / 120"
                if (s?.length!! > 120) {
                    tvStringtilEoVisionCount.setTextColor(Color.parseColor("#e74c3c"))
                } else {
                    tvStringtilEoVisionCount.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val now = sdf.format(Date())

        signUpButton2.setOnClickListener {
            if (
                email.text.toString() == "" ||
                username.text.toString() == "" ||
                passwordRetype.text.toString() != password.text.toString() ||
                misi.text.toString() == "" ||
                accountName.text.toString() == "" ||
                accountBankName.text.toString() == "" ||
                accountNumber.text.toString() == "" ||
                passwordRetype.text.toString().length < 5
            ) {
                toast("Please complete this form")
            } else {
                user.eoEmail = email.text.toString()
                user.eoName = username.text.toString()
                user.eoPassword = passwordRetype.text.toString()
                user.eoPoint = "0"
                user.eoVision = misi.text.toString()
                user.eoMission = visi.text.toString()
                user.accountName = accountName.text.toString()
                user.bankName = accountBankName.text.toString()
                user.accountRek = accountNumber.text.toString()
                uploadImage()
            }
        }
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
                /*
                 * Get the column indexes of the data in the Cursor,
                 * move to the first row in the Cursor, get the data,
                 * and display it.
                 */
//                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()

                fileNameImage = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                fileNameUpload.text = fileNameImage
                user.eoPhoto = fileNameImage
            }
        }
    }

    private fun uploadImage() {
        presenter.addUser(user)
        val storage = FirebaseStorage.getInstance()
        val storageReference: StorageReference? = storage.reference
        if (filePath != null) {
            Log.i("cek", filePath.toString())
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference?.child("userProfileImage/" + user.eoPhoto)
            ref?.putFile(filePath!!)
                ?.addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
                    alertDialog()
                }
                ?.addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                ?.addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
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
    private fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        view.titleFillFormSubmitted.text = "Congratulation's now your accout is added"
        view.btnSubmitFillForm.setOnClickListener {
            startActivity(intentFor<LoginPresenter>())

        }
        builder.setView(view)
        builder.show()
    }
}
