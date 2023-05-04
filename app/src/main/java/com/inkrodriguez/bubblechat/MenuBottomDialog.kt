package com.inkrodriguez.bubblechat

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterPublications
import com.inkrodriguez.bubblechat.data.Publication
import kotlinx.coroutines.launch

class MenuBottomDialog() : BottomSheetDialogFragment() {

    private var connect = Firebase.firestore
    private lateinit var intent: Intent

    fun setIntent(intent: Intent) {
        this.intent = intent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.menubottomdialog, container, false)

        val btnSaved = view.findViewById<LinearLayout>(R.id.btnSaved_MenuBottomDialog)

        btnSaved.setOnClickListener {
            startActivity(Intent(context, SavedPostsActivity::class.java))
        }

        return view
    }

}
