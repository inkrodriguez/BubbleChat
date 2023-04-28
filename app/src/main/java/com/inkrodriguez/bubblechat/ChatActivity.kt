package com.inkrodriguez.bubblechat

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterContatos
import com.inkrodriguez.bubblechat.data.Friend
import com.inkrodriguez.bubblechat.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private var connect = Firebase.firestore
    private var db = connect
    private lateinit var binding: ActivityChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var recyclerView = binding.recyclerView

        val sharedPref: SharedPreferences? = this.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val valorSalvo = sharedPref?.getString("USERNAME", "NADA ENCONTRADO")
        val listFriends = mutableListOf<Friend>()

        db.collection("users").document("$valorSalvo")
            .get().addOnSuccessListener {
                var friend = it.get("friends") as MutableList<*>

                friend.forEach { f ->
                    listFriends.add(Friend("$f"))

                    val adapter = AdapterContatos(listFriends)

                    recyclerView.adapter = adapter
                }

            }.addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}
