package com.inkrodriguez.bubblechat.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity
import com.inkrodriguez.bubblechat.data.Friend


class AdapterContatos(private val characters: MutableList<Friend>, private val context: Context): RecyclerView.Adapter<AdapterContatos.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_messages, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
        var username = characters[position].username

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserActivity::class.java)
            intent.putExtra("username", username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: Friend){
            with(itemView){
                val nome = findViewById<TextView>(R.id.tvNome)
                val fotoPerfil = findViewById<ImageView>(R.id.imageViewItemMessages)

                nome.text = data.username
                readDataUser(fotoPerfil, data.username, context)


            }
        }

        fun readDataUser(imagePerfil: ImageView, username: String, context: Context){
            val db = Firebase.firestore
            db.collection("users").whereEqualTo("username", username).addSnapshotListener { value, error ->
                value?.documents?.forEach {
                    var url = it.get("fotoperfil")
                    Glide.with(context).load(url).into(imagePerfil)
                }
            }
        }
    }
}