package com.team.oleg.funder.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.team.oleg.funder.Company.CompanyActivity
import com.team.oleg.funder.Login.LoginEO.LoginEOActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.SharedPreferenceUtils
import kotlinx.android.synthetic.main.experiment_firebase.*
import org.jetbrains.anko.intentFor

class LoginPresenter : AppCompatActivity() {

    private var USER_ID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.experiment_firebase)
        val sharedPref = this.getSharedPreferences(SharedPreferenceUtils.USER_LOGIN, 0) ?: return
        val USER_TYPE = sharedPref.getString(SharedPreferenceUtils.USER_TYPE, SharedPreferenceUtils.EMPTY)
        USER_ID = sharedPref.getString(SharedPreferenceUtils.USER_ID, SharedPreferenceUtils.EMPTY)

        if (USER_TYPE != SharedPreferenceUtils.EMPTY) {
            when (USER_TYPE) {
                SharedPreferenceUtils.USER_EO -> {
                    startActivity(intentFor<MainActivity>())
                }
                SharedPreferenceUtils.USER_COMPANY -> {
                    startActivity(intentFor<CompanyActivity>())
                }
                else -> {
                    startActivity(intentFor<LoginEOActivity>())
                }
            }
        } else {
            startActivity(intentFor<LoginEOActivity>())
        }

//        realtimeUpdateListener()
//        btnSendMesage.setOnClickListener {
//            sendMessage()
//        }

    }

//    companion object {
//        const val COLLECTION_KEY = "Chat"
//        const val DOCUMENT_KEY = "Message"
//        const val NAME_FIELD = "Name"
//        const val TEXT_FIELD = "Text"
//    }
//
//    private val firestoreChat by lazy {
//        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
//    }
//
//    private fun sendMessage(){
//        val newMesage = mapOf(
//            NAME_FIELD to nameUser.text.toString(),
//            TEXT_FIELD to message.text.toString()
//        )
//
//        firestoreChat.set(newMesage)
//            .addOnSuccessListener {
//                Toast.makeText(this@LoginPresenter,"Message Sent",Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                e -> Log.i("ERROR",e.message)
//            }
//    }
//
//    private fun realtimeUpdateListener(){
//        firestoreChat.addSnapshotListener {
//                documentSnapshot,
//                firebaseFirestoreException ->
//            when {
//                firebaseFirestoreException != null -> Log.i("ERROR", firebaseFirestoreException.message)
//                documentSnapshot != null && documentSnapshot.exists() -> {
//                    with(documentSnapshot){
//                        displayMessage.text = "${data?.get(NAME_FIELD)}:${data?.get(TEXT_FIELD)}"
//                    }
//                }
//            }
//
//        }
//    }
}