package com.team.oleg.funder.Service

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.team.oleg.funder.R

class MessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful){
                    Log.w("FirebaseService","getInstanceId",task.exception)
                }

                // Get New Instance Id Token
                val token = task.result?.token

                // Log and toast
                Log.i("FirebaseService",token)
            })

    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i("FirebaseService","Refreshed token: $token")
    }
}