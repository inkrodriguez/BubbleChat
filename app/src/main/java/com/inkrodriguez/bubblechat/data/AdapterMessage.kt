package com.inkrodriguez.bubblechat.data

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity


class AdapterMessage(private val characters: List<Chat>): RecyclerView.Adapter<AdapterMessage.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_chat, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
        //holder.itemView.setOnClickListener { Toast.makeText(it.context, "Oii", Toast.LENGTH_SHORT).show() }
        holder.itemView.setOnClickListener {
            //Toast.makeText(it.context, characters[position].messages, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: Chat) {
            with(itemView) {
                val message = findViewById<TextView>(R.id.tvChatRemetente)
                message.text = data.message
            }
        }
    }
}