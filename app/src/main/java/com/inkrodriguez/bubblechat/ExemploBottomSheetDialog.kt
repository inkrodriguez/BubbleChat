package com.inkrodriguez.bubblechat

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class ExemploBottomSheetDialog() : BottomSheetDialogFragment() {

    private var connect = Firebase.firestore
    private lateinit var intent: Intent

    fun setIntent(intent: Intent) {
        this.intent = intent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheetdialog, container, false)

        val image = view.findViewById<ImageView>(R.id.imagePerfilSheetDialog)
        val tvUsername = view.findViewById<TextView>(R.id.tvUsernamePerfilSheetDialog)
        val tvLike = view.findViewById<TextView>(R.id.tvLikesPerfilSheetDialog)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitlePerfilSheetDialog)
        val tvSubtitle = view.findViewById<TextView>(R.id.tvSubtitlePerfilSheetDialog)

        val urlIntent = intent.getStringExtra("url").toString()

        Glide.with(this).load(urlIntent).into(image)

        readDataPublication(urlIntent, tvUsername, tvLike, tvTitle, tvSubtitle)

        return view
    }

    private fun readDataPublication(urlIntent: String, tvUsername: TextView, tvLike: TextView, tvTitle: TextView, tvSubtitle: TextView){
        lifecycleScope.launch {

            val db = Firebase.firestore
            val usersRef = db.collection("publications")
            val query = usersRef.whereEqualTo("url", urlIntent)

            val registration = query.addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(
                        ContentValues.TAG,
                        "Erro ao ouvir as publicações do usuário $urlIntent",
                        error
                    )
                    return@addSnapshotListener
                }

                for (document in value!!) {
                    val url = document.getString("url").toString()
                    val username = document.getString("username").toString()
                    val numberLikes = document.getString("like").toString()
                    val title = document.getString("title").toString()
                    val subtitle = document.getString("subtitle").toString()

                    tvUsername.text = username
                    tvLike.text = "$numberLikes likes"
                    tvTitle.text = title
                    tvSubtitle.text = subtitle

                }

            }

            // Quando quiser parar de ouvir mudanças
            // registration.remove()

        }
    }
}
