package com.inkrodriguez.bubblechat

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterPublications
import com.inkrodriguez.bubblechat.data.Publication
import com.inkrodriguez.bubblechat.databinding.FragmentPerfilBinding
import kotlinx.coroutines.launch


class PerfilFragment : Fragment() {

    private var connect = Firebase.firestore
    private var db = connect
    private lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPref: SharedPreferences? =
            context?.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()

        val tvName = binding.tvNamePerfil
        val tvOcupation = binding.tvOcupationPerfil
        val tvBiography = binding.tvBiographyPerfil
        val tvLink = binding.tvLinkPerfil
        val tvFriendsSize = binding.tvFriendsSize
        val tvPublicationsSize = binding.tvPublicationsSize
        val recyclerView = binding.recyclerView
        val btnEditProfile = binding.btnEditProfile

        btnEditProfile.setOnClickListener {
            //startActivity(Intent(context, SettingsActivity::class.java, null))
        }

        //traz todas as informações sobre o usuário
        readData(sharedPrefencesValue, tvName, tvOcupation, tvBiography, tvLink, tvFriendsSize)

        lifecycleScope.launch {

            val db = Firebase.firestore
            val usersRef = db.collection("publications")
            val query = usersRef.whereEqualTo("username", sharedPrefencesValue)
            val listPosts: MutableList<Publication> = mutableListOf()

            val registration = query.addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Erro ao ouvir as publicações do usuário $sharedPrefencesValue", error)
                    return@addSnapshotListener
                }

                listPosts.clear()

                for (document in value!!) {
                    val urls = document.getString("url").toString()
                    listPosts.add(Publication(urls))
                }

                var adapter = AdapterPublications(listPosts, context?.applicationContext!!)
                recyclerView.adapter = adapter
            }

            // Quando quiser parar de ouvir mudanças
           // registration.remove()

        }


            return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun readData(sharedPrefencesValue: String, tvName: TextView, tvOcupation: TextView,
                 tvBiography: TextView, tvLink: TextView, tvFriendsSize: TextView){

        db.collection("users").document(sharedPrefencesValue).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
            } else {

                tvName.text = value?.get("nome").toString()
                tvOcupation.text = value?.get("ocupation").toString()
                tvBiography.text = value?.get("biography").toString()
                tvLink.text = value?.get("link").toString()

                val list: MutableList<Any?> = mutableListOf(value?.get("friends"))
                val friendsList = list.firstOrNull() as? List<*>
                val friendsListSize = friendsList?.size ?: 0
                tvFriendsSize.text = "$friendsListSize"

            }
        }
    }

}