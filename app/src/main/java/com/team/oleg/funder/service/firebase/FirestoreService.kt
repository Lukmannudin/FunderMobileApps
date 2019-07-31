package com.team.oleg.funder.service.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


object FirestoreService {

    val TAG = "FirestoreService"

    fun onBiddingSponbsor(bid: HashMap<String, String?>) {
        val db = FirebaseFirestore.getInstance()

        db.collection("bidder")
            .add(bid)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Log.w(TAG, "Error adding document", e)
            }

    }
}