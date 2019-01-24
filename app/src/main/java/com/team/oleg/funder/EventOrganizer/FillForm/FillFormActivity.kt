package com.team.oleg.funder.EventOrganizer.FillForm

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_fill_form.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class FillFormActivity : AppCompatActivity(), FillFormContract.View {

    override lateinit var presenter: FillFormContract.Presenter
    private var filePath: Uri? = null
    private var fileNameImage: String? = null
    private val PICK_IMAGE_REQUEST = 71
    private var USER_NAME: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        val sponsorData = intent.getParcelableExtra<Sponsor>(Utils.INTENT_PARCELABLE)
        backFillForm.setOnClickListener {
            startActivity(
                intentFor<AuctionActivity>(
                    Utils.INTENT_PARCELABLE to sponsorData
                )
            )
        }

        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val USER_ID = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        USER_NAME = sharedPref.getString(SharedPreferenceUtils.USER_NAME, SharedPreferenceUtils.EMPTY)

        presenter = FillFormPresenter(this)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val now = sdf.format(Date())

        btnSubmitFillForm.setOnClickListener {
            presenter.addEvent(
                Event(
                    null,
                    USER_ID,
                    sponsorData.sponsorId,
                    edtEventName.text.toString(),
                    edtEventDate.text.toString(),
                    edtEventSpeaker.text.toString(),
                    edtEventMp.text.toString(),
                    edtEventDesc.text.toString(),
                    "$USER_NAME-$now",
                    Utils.STATUS_AVAILABLE
                )
            )
        }

        btnUploadProposal.setOnClickListener {
            chooseFile()
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        edtEventDate.setOnFocusChangeListener { v, hasFocus ->
            DatePickerDialog(
                this@FillFormActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
            updateDateInView()
            edtEventDate.clearFocus()
        }
    }

    private fun updateDateInView() {
//        val myFormat = "dd MMMM yyyy"
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edtEventDate.setText(sdf.format(cal.time))
    }


    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessageSuccess(message: String) {
        uploadImage()
    }

    override fun showFailedMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
                fileChooserFillForm.text = fileNameImage

            }

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    var cal = Calendar.getInstance()


    private fun alertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        view.btnSubmitFillForm.setOnClickListener {
            startActivity(intentFor<MainActivity>())

        }
        builder.setView(view)
        builder.show()
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        var storageReference: StorageReference? = storage.reference
        if (filePath != null) {
            Log.i("cek", filePath.toString())
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val myFormat = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val now = sdf.format(Date())
            val ref = storageReference?.child("proposal/{$USER_NAME}-{$now}")
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

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
}
