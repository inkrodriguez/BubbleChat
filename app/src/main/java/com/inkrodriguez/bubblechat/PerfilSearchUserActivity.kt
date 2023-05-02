package com.inkrodriguez.bubblechat

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterPublications
import com.inkrodriguez.bubblechat.data.Publication
import com.inkrodriguez.bubblechat.databinding.ActivityPerfilSearchUserBinding
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

class PerfilSearchUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilSearchUserBinding

    private var connect = Firebase.firestore
    private var db = connect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilSearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPref: SharedPreferences? = this.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()


        val tvName = binding.tvNamePerfil
        val tvOcupation = binding.tvOcupationPerfil
        val tvBiography = binding.tvBiographyPerfil
        val tvLink = binding.tvLinkPerfil
        val tvFriendsSize = binding.tvFriendsSize
        val intent = intent.getStringExtra("username").toString()
        val fotoPerfil = binding.fotoPerfil
        val tvPublicationsSize = binding.tvPublicationsSize
        val recyclerView = binding.recyclerView
        val btnEditProfile = binding.btnEditProfile
        val fragmentManager: FragmentManager = supportFragmentManager

        readDataUsers(intent, fotoPerfil, tvName, tvOcupation, tvBiography, tvLink, tvFriendsSize)
        readDataPublications(sharedPrefencesValue, fragmentManager, recyclerView, tvPublicationsSize)

    }

    private fun readDataPublications(sharedPrefencesValue: String, fragmentManager: FragmentManager, recyclerView: RecyclerView,
                                     tvPublicationsSize: TextView) {

        lifecycleScope.launch {

            val getIntent = intent.getStringExtra("username")

            val db = Firebase.firestore
            val usersRef = db.collection("publications")
            val query = usersRef.whereEqualTo("username", getIntent)
            val listPosts: MutableList<Publication> = mutableListOf()

            val registration = query.addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(
                        ContentValues.TAG,
                        "Erro ao ouvir as publicações do usuário $sharedPrefencesValue",
                        error
                    )
                    return@addSnapshotListener
                }

                listPosts.clear()

                for (document in value!!) {
                    val urls = document.getString("url").toString()
                    listPosts.add(Publication(url = urls))
                }

                tvPublicationsSize.text = listPosts.size.toString()

                var adapter = AdapterPublications(listPosts, fragmentManager)
                recyclerView.adapter = adapter
            }

            // Quando quiser parar de ouvir mudanças
            // registration.remove()

        }
    }


    private fun readDataUsers(intent: String, fotoPerfil: CircleImageView, tvName: TextView, tvOcupation: TextView,
                 tvBiography: TextView, tvLink: TextView, tvFriendsSize: TextView
    ){

        db.collection("users").document(intent).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            } else {

                tvName.text = value?.get("nome").toString()
                tvOcupation.text = value?.get("ocupation").toString()
                tvBiography.text = value?.get("biography").toString()
                tvLink.text = value?.get("link").toString()

                val urlFotoPerfil = value?.get("fotoperfil").toString()
                Glide.with(this.applicationContext).load(urlFotoPerfil).into(fotoPerfil)

                val list: MutableList<Any?> = mutableListOf(value?.get("friends"))
                val friendsList = list.firstOrNull() as? List<*>
                val friendsListSize = friendsList?.size ?: 0
                tvFriendsSize.text = "$friendsListSize"

            }
        }
    }
}