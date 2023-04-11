package com.inkrodriguez.bubblechat.data

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.Conversas
import com.inkrodriguez.bubblechat.HomeActivity
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity


class AdapterContatos(private val characters: MutableList<Friend>): RecyclerView.Adapter<AdapterContatos.CharacterViewHolder>() {

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
                //val pontuacao = findViewById<TextView>(R.id.tvPontuacao)

                nome.text = data.username
                //pontuacao.text = data.pontuacao
            }
        }
    }
}