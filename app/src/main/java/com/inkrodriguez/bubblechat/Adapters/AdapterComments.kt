package com.inkrodriguez.bubblechat.Adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.data.Comment

class AdapterComments(private val comment: List<Comment>): RecyclerView.Adapter<AdapterComments.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = holder.bind(comment[position])
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Comment) {
            with(itemView) {
                val sharedPref: SharedPreferences? = context.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
                val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()
                val db = Firebase.firestore

                val image = findViewById<ImageView>(R.id.imgViewUserComment)
                val tvUsername = findViewById<TextView>(R.id.tvUsernameComment)
                val tvComment = findViewById<TextView>(R.id.tvComment)
                val link = data.url

                tvUsername.text = data.sender
                tvComment.text = data.comment

                db.collection("users").document(data.sender).addSnapshotListener { value, error ->
                    if (error != null) {
                        Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        val urlImagePerfil = value?.get("fotoperfil")
                        Glide.with(context).load(urlImagePerfil).into(image)
                    }
                }
            }
        }
    }
}