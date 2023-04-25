package com.inkrodriguez.bubblechat.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity
import com.inkrodriguez.bubblechat.data.User


class AdapterUsers(private val users: MutableList<User>): RecyclerView.Adapter<AdapterUsers.UsersViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_messages, parent, false)
            return UsersViewHolder(view)
        }

        override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
            holder.bind(users[position])
            var username = users[position].nome

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, UserActivity::class.java)
                intent.putExtra("username", username)
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return users.size
        }

        //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
        class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
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