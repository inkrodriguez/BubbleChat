package com.inkrodriguez.bubblechat

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.data.*
import com.inkrodriguez.bubblechat.databinding.ActivityUserBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private var connect = Firebase.firestore
    private var db = connect
    private var firebaseUtils = FirebaseUtils()

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

        firebaseUtils.fireBinding = this.binding

        binding.btnEnviarMessage.setOnClickListener {
            enviarMensagem()
            exibir()
            binding.editMessage.setText("")
        }
    }


    //APARECER OS DADOS DA LISTA
    //CLICAR NO BOTÃO E ENVIAR A MENSAGEM

    fun exibir() {
        var recyclerView = binding.recyclerViewChat
        val db = Firebase.firestore
        db.collection("messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val messages = result.documents.map { document ->
                    val message = document.getString("message")
                    val date = document.getString("date")
                    val remetente = document.getString("remetente")
                    Chat(message = message.toString(), date = date.toString(), remetente = remetente.toString())
                }
                val adapter = AdapterMessage(messages)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


    var mesAtual: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun receiveData(): LocalDateTime{
        return LocalDateTime.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatData(): String {
        var month = receiveData().month
        val dayOfMonth = receiveData().dayOfMonth
        val dayOfYear = receiveData().year
        val hour = receiveData().hour
        val minute = receiveData().minute
        val second = receiveData().second
        var secondFormat: String
        var minuteFormat: String
        var hourFormat: String

        when(month.value) {
            1 -> mesAtual = "janeiro"
            2 -> mesAtual = "fevereiro"
            3 -> mesAtual = "março"
            4 -> mesAtual = "abril"
            5 -> mesAtual = "maio"
            6 -> mesAtual = "junho"
            7 -> mesAtual = "julho"
            8 -> mesAtual = "agosto"
            9 -> mesAtual = "setembro"
            10 -> mesAtual = "outubro"
            11 -> mesAtual = "novembro"
            12 -> mesAtual = "dezembro"
        }

        secondFormat = addZeroBeforeNumber(second)
        minuteFormat = addZeroBeforeNumber(minute)
        hourFormat = addZeroBeforeNumber(hour)
        return "$dayOfMonth de $mesAtual de $dayOfYear às $hourFormat:$minuteFormat:$secondFormat UTC-3"

    }

    private fun addZeroBeforeNumber(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarMensagem() {
        lifecycleScope.launch {
            val chatCollection = db.collection("messages")
            var message = binding.editMessage.text.toString()
            var userAtual = "Sara"

            var messageMap = hashMapOf(
                "date" to formatData(),
                "remetente" to userAtual,
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
