package com.inkrodriguez.bubblechat.data

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.databinding.ActivityUserBinding


class FirebaseUtils {

    lateinit var fireBinding: ActivityUserBinding
    private var connect = Firebase.firestore
    private var db = connect


    fun getCount(): Int? {
        var snapshot: Int? = null
        db.collection("chat").document().addSnapshotListener { value, error ->
            if(value != null){
                snapshot = value.id.length
            }
        }
        return snapshot
    }

}