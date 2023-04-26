package com.inkrodriguez.bubblechat.Adapters

import android.content.Context
import android.content.Intent
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
import com.inkrodriguez.bubblechat.PerfilSearchUserActivity
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.UserActivity
import com.inkrodriguez.bubblechat.data.User


class AdapterUsers(private val context: Context? , private val users: MutableList<User>): RecyclerView.Adapter<AdapterUsers.UsersViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_user_search, parent, false)
            return UsersViewHolder(view)
        }

        override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
            val sharedPref: SharedPreferences? = context?.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
            val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()

            holder.bind(users[position])
            var username = users[position].nome

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, PerfilSearchUserActivity::class.java)
                intent.putExtra("username", username)
                val getIntent = intent.getStringExtra("username")

                //Caso a intent seja diferente do sharedPreferences.
                if(getIntent != sharedPrefencesValue){
                    Toast.makeText(context, "$getIntent e $sharedPrefencesValue (DIFERENTE)", Toast.LENGTH_SHORT).show()
                    holder.itemView.context.startActivity(intent)
                } else {
                    Toast.makeText(context, "You can access your own profile through the Profile tab in the menu below!", Toast.LENGTH_SHORT).show()
                }

            }
        }

        override fun getItemCount(): Int {
            return users.size
        }

        //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
        class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            fun bind(data: User) {
                with(itemView) {
                    val nome = findViewById<TextView>(R.id.tvNameSearch)
                    val ocupation = findViewById<TextView>(R.id.tvOcupationSearch)
                    val fotoPerfil = findViewById<ImageView>(R.id.imgViewUserSearch)

                    nome.text = data.nome
                    ocupation.text = data.ocupation
                    Glide.with(context).load(data.fotoperfil).into(fotoPerfil)
                }
            }
        }
}