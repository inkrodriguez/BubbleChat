package com.inkrodriguez.bubblechat

import android.R
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.data.*
import com.inkrodriguez.bubblechat.databinding.ActivityUserBinding
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.Instant


class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var adapter: AdapterMessage
    private var connect = Firebase.firestore
    private var db = connect

    override fun onStart() {
        super.onStart()
        exibir()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var intent = intent.getStringExtra("username")

        val editMessage = binding.editMessage.text

        getStatus()

        binding.btnEnviarMessage.setOnClickListener {
            if(editMessage.isEmpty()){
                Toast.makeText(this, "Digite alguma mensagem", Toast.LENGTH_SHORT).show()
            } else {
                sendMessage()
                exibir()
                binding.editMessage.setText("")
                Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

        private fun getStatus() {
                val db = FirebaseFirestore.getInstance()
                val docRef = db.collection("users").document("inkrodriguez")
                docRef.addSnapshotListener { snapshot, e ->
                    try {
                        if (e != null) {
                            Toast.makeText(this, "Listen failed. $e", Toast.LENGTH_SHORT).show()
                            return@addSnapshotListener
                        }

                        if (snapshot != null && snapshot.exists()) {
                            val data = snapshot.data
                            // Aqui você pode acessar o dado que deseja exibir, por exemplo:
                            val status = data?.get("status") as? String
                            if (status != null) {
                                binding.tvStatusUser.text = status
                                Toast.makeText(this, "Current data: $data", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Invalid status value: $status", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Current data: null", Toast.LENGTH_SHORT).show()
                        }
                    } catch (ex: Exception) {
                        // Trate a exceção adequadamente
                        Toast.makeText(this@UserActivity, "Error getting status, $ex", Toast.LENGTH_SHORT).show()
                }
            }
        }



//        db.collection("users").document("inkrodriguez").addSnapshotListener { value, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (value != null) {
//                val status = value.getString("status")
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//        }


    fun exibir() {
        val sharedPref: SharedPreferences = applicationContext.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val valorSalvo = sharedPref?.getString("USERNAME", "NADA ENCONTRADO")
        var intent = intent.getStringExtra("username")
        val extras = Bundle()
        extras.putString("valorSalvo", valorSalvo.toString())
        extras.putString("usuarioAtual", intent.toString())


        var recyclerView = binding.recyclerViewChat
        val db = Firebase.firestore
        db.collection("messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.map { document ->
                        val message = document.getString("message")
                        val date = document.getDate("date")
                        val remetente = document.getString("remetente")
                        Chat(message = message.toString(), date = date.toString(), remetente = remetente.toString())
                    }
                    adapter = AdapterMessage(messages, extras)
                    recyclerView.adapter = adapter
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun receiveDate(): java.util.Date? {
        val instant = Instant.now()
        return Timestamp.from(instant)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage() {
        lifecycleScope.launch {
            var intent = intent.getStringExtra("username")
            val chatCollection = db.collection("messages")
            var message = binding.editMessage.text.toString()
            val sharedPref = applicationContext.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
            val remetente = sharedPref.getString("USERNAME", "SEM DADOS")

            var destinatario = intent

            var messageMap = hashMapOf(
                "date" to receiveDate(),
                "remetente" to remetente,
                "destinatario" to destinatario,
                "message" to message
            )

            chatCollection.add(messageMap).addOnSuccessListener {
            }
        }
        }



}
