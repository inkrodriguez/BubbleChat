package com.inkrodriguez.bubblechat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.data.*
import com.inkrodriguez.bubblechat.databinding.ActivityUserBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private var connect = Firebase.firestore
    private var db = connect
    private var firebaseUtils = FirebaseUtils()

    override fun onStart() {
        super.onStart()
        getList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var intent = intent.getStringExtra("id")
        firebaseUtils.fireBinding = this.binding

        binding.btnEnviarMessage.setOnClickListener {
            getList()
            enviarMensagem()
        }

    }

    fun getList() {
        lifecycleScope.launch {
            var lista: MutableList<Chat> = mutableListOf()
            db = FirebaseFirestore.getInstance()

            db.collection("messages").addSnapshotListener { value, error ->
                value?.documents?.forEach {

                    var recyclerView = binding.recyclerViewChat
                    var adapter = AdapterMessage(lista)
                    adapter.notifyItemChanged(lista.size)
                    recyclerView.adapter = adapter

                    lista.add(Chat(message = it.get("message").toString()))


                }
            }
        }
    }

    fun enviarMensagem() {
        lifecycleScope.launch {
            getList()
            var message = binding.editMessage.text.toString()

                var messageMap = hashMapOf(
                    "message" to message
                )

                db.collection("messages").document("3").set(messageMap).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@UserActivity, "tudo ok", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                }
        }
    }
}