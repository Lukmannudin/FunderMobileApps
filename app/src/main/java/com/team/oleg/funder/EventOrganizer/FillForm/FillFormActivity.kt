package com.team.oleg.funder.EventOrganizer.FillForm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.oleg.funder.Auction.AuctionActivity
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.Data.Sponsor
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_fill_form.*
import org.jetbrains.anko.intentFor
import java.io.IOException
import java.util.*

class FillFormActivity : AppCompatActivity(), FillFormContract.View {

    override lateinit var presenter: FillFormContract.Presenter
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

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
        val USER_NAME = sharedPref.getString(SharedPreferenceUtils.USER_NAME, SharedPreferenceUtils.EMPTY)

        presenter = FillFormPresenter(this)


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
                    "${sponsorData.sponsorName}-${USER_NAME}"+ Date().toString(),
                    Utils.STATUS_AVAILABLE
                )
            )
        }

        btnUploadProposal.setOnClickListener {
            chooseImage()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessageError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun showMessageSuccess(message: String) {

        uploadImage()

    }

    override fun showSuccessMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFailedMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()

        var storageReference: StorageReference?  = storage.reference
        if (filePath != null) {
            Log.i("cek",filePath.toString())
//            val progressDialog = ProgressDialog(this)
//            progressDialog.setTitle("Uploading...")
//            progressDialog.show()

            val ref = storageReference?.child("proposal/" + UUID.randomUUID().toString())
            ref?.putFile(filePath!!)
                ?.addOnSuccessListener {
                    titleFillForm.text = "SUCCESS"
//                    progressDialog.dismiss()
//                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
//                    startActivity(intentFor<LoginPresenter>())
                }
                ?.addOnFailureListener { e ->
//                    progressDialog.dismiss()
//                    Toast.makeText(applicationContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                ?.addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
//                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
}
