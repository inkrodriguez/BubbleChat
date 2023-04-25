package com.inkrodriguez.bubblechat.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity
import com.inkrodriguez.bubblechat.data.Friend
import com.inkrodriguez.bubblechat.data.Publication


class AdapterPublications(private val publication: MutableList<Publication>): RecyclerView.Adapter<AdapterPublications.PublicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_publication, parent, false)
        return PublicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        holder.bind(publication[position])
        var username = publication[position].publications

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserActivity::class.java)
            intent.putExtra("username", username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return publication.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class PublicationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: Publication){
            with(itemView){
                val image = findViewById<ImageView>(R.id.imagePublication)
                val link = data.publications

                Glide.with(context).load("$link").into(image)
            }
        }
    }
}