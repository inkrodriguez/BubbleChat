package com.inkrodriguez.bubblechat

import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var intent = intent.getStringExtra("username")

        binding.btnEnviarMessage.setOnClickListener {
            sendMessage()
            exibir()
            binding.editMessage.setText("")
            Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show()

        }
    }


    fun exibir() {
       var intent = intent.getStringExtra("username")

        val sharedPref = applicationContext.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val valorSalvo = sharedPref?.getString("USERNAME", "NADA ENCONTRADO")
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
                        //val date = document.getDate("date")
                        val remetente = document.getString("remetente")
                        Chat(message = message.toString(), remetente = remetente.toString())
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
                soundSendingMessage()
            }
        }
        }

    fun soundSendingMessage(){
        val mediaPlayer = MediaPlayer.create(this, R.raw.enviomessage)
        mediaPlayer.start()

    }

}
