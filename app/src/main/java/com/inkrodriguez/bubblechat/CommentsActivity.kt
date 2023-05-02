package com.inkrodriguez.bubblechat

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterComments
import com.inkrodriguez.bubblechat.data.Comment
import com.inkrodriguez.bubblechat.databinding.ActivityCommentsBinding

class CommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentsBinding
    private var db = Firebase.firestore
    private lateinit var commentsRef: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPref: SharedPreferences? = this.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()
        val extras = intent.extras
        val usernameExtras = extras?.getString("username").toString()
        val urlExtras = extras?.getString("url").toString()

        val listComments: MutableList<Comment> = mutableListOf()
        val recyclerView = binding.recyclerViewComments
        val adapter = AdapterComments(listComments)

        val imageCommentUser = binding.imgPerfilComments
        val tvUsernameComment = binding.tvUsernameComment
        val tvTitleComment = binding.tvTitleComment

        commentsRef = db.collection("comments")

        val inflater = LayoutInflater.from(this.applicationContext)
        val layout = inflater.inflate(R.layout.layout_bottom_comment, binding.root)

        val imageCurrentUser = layout.findViewById<ImageView>(R.id.imageLayoutBottomComment)
        val editComment = layout.findViewById<EditText>(R.id.editTextCommentSheetDialogComment)
        val btnPublish = layout.findViewById<Button>(R.id.btnPublishCommentSheetDialogComment)

        db.collection("users").document(sharedPrefencesValue).addSnapshotListener { value, error ->
            val urlImage = value?.getString("fotoperfil")
            Glide.with(this).load(urlImage).into(imageCurrentUser)
        }

        db.collection("users").document(usernameExtras).addSnapshotListener { value, error ->
            val urlImage = value?.getString("fotoperfil")
            Glide.with(this).load(urlImage).into(imageCommentUser   )
        }

        readComments(recyclerView, adapter, imageCurrentUser, imageCommentUser, sharedPrefencesValue, usernameExtras, urlExtras, tvUsernameComment, tvTitleComment)

        btnPublish.setOnClickListener {
            recordComments(editComment, sharedPrefencesValue, usernameExtras, urlExtras, adapter)
        }

    }

    private fun recordComments(editComment: EditText, sharedPreferencesValue: String, username: String, url: String, adapterComments: AdapterComments){
        binding.progressBar.visibility = View.VISIBLE
        db.collection("comments").add(Comment(
            comment = editComment.text.toString(),
            sender = sharedPreferencesValue,
            receiver = username,
            url = url
        )).addOnSuccessListener {
            adapterComments.notifyDataSetChanged()
            editComment.setText("")
        }.addOnFailureListener {
            Toast.makeText(this, "There was an error sending the comment!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readComments(recyclerView: RecyclerView, adapterComments: AdapterComments, imageCurrentUser: ImageView, imgPerfil: ImageView,
    sharedPreferencesValue: String, usernameExtras: String, urlExtras: String, tvUsernameComment: TextView, tvTitleComment: TextView){

        commentsRef.whereEqualTo("url", urlExtras).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val comment = snapshot.documents.map {
                        Comment(
                            sender = it.getString("sender").toString(),
                            receiver = it.getString("receiver").toString(),
                            comment = it.getString("comment").toString(),
                            url = it.getString("url").toString()
                        )
                    }

                    recyclerView.adapter = AdapterComments(comment)

                    db.collection("publications").whereEqualTo("url", urlExtras).get().addOnSuccessListener { value ->

                        value.documents.forEach {
                            val title = it.getString("title")
                            val username = it.getString("username")

                            tvUsernameComment.text = username
                            tvTitleComment.text = title
                        }
                    }

                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
    }
}