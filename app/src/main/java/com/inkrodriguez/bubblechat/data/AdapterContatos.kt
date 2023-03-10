package com.inkrodriguez.bubblechat.data

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity


class AdapterContatos(private val characters: List<User>): RecyclerView.Adapter<AdapterContatos.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_messages, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position], )


    }

    override fun getItemCount(): Int {
        return characters.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: User){
            with(itemView){
                val nome = findViewById<TextView>(R.id.tvNome)
                //val pontuacao = findViewById<TextView>(R.id.tvPontuacao)

                nome.text = data.nome
                //pontuacao.text = data.pontuacao
            }
        }
    }
}