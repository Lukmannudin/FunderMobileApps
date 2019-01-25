package com.team.oleg.funder.EventOrganizer.FillForm

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_fill_form.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class FillFormActivity : AppCompatActivity(), FillFormContract.View {

    override lateinit var presenter: FillFormContract.Presenter
    private var filePath: Uri? = null
    private var fileNameImage: String? = null
    private val PICK_IMAGE_REQUEST = 71
    private var USER_NAME: String? = null
    private val event = Event()

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.team.oleg.funder.R.layout.activity_fill_form)

        val sponsorData = intent.getParcelableExtra<Sponsor>(Utils.INTENT_PARCELABLE)
        backFillForm.setOnClickListener {
            startActivity(intentFor<AuctionActivity>(Utils.INTENT_PARCELABLE to sponsorData))
        }

        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val USER_ID = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)
        USER_NAME = sharedPref.getString(SharedPreferenceUtils.USER_NAME, SharedPreferenceUtils.EMPTY)

        presenter = FillFormPresenter(this)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val now = sdf.format(Date())

        btnSubmitFillForm.setOnClickListener {
            event.eoId = USER_ID
            event.sponsorId = sponsorData.sponsorId
            event.eventName = edtEventName.text.toString()
            event.eventDate = edtEventDate.text.toString()
            event.eventSpeaker = edtEventSpeaker.text.toString()
            event.eventMp = edtEventMp.text.toString()
            event.eventDesc = edtEventDesc.text.toString()
            event.eventStatus = Utils.STATUS_AVAILABLE
            event.eventDana = edtDemandFunding.text.toString()
            presenter.addEvent(event)
        }

        dlIconProposal.setOnClickListener { downloadFile() }
        btnUploadProposal.setOnClickListener { chooseFile() }

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        edtEventDate.setOnTouchListener { _, action ->
            if (action.action == MotionEvent.ACTION_UP) {
                DatePickerDialog(
                    this@FillFormActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            true
        }
    }

    private fun downloadFile() {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference? = storage.reference

        storageRef?.child("proposal_idaman.pdf")?.downloadUrl?.addOnSuccessListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, it)
            startActivity(browserIntent)
        }?.addOnFailureListener { Log.i("file", it.localizedMessage) }
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
        toast(message)
    }

    override fun showMessageSuccess(message: String) = uploadImage()

    override fun showFailedMessage(message: String) {
        toast(message)
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
                event.eventProposal = fileNameImage
            }
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
        }
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(com.team.oleg.funder.R.layout.custom_dialog, null)
        view.btnSubmitFillForm.setOnClickListener { startActivity(intentFor<MainActivity>()) }
        builder.setView(view)
        builder.show()
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        val storageReference: StorageReference? = storage.reference
        if (filePath != null) {
            Log.i("cek", filePath.toString())
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val myFormat = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val now = sdf.format(Date())
            val ref = storageReference?.child("proposal/${event.eventProposal}")
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
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_IMAGE_REQUEST)
    }
}
