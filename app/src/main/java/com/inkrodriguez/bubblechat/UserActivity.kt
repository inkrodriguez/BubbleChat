package com.inkrodriguez.bubblechat

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
//            recoverId {
//                var docId = it?.substringBefore(",").toString().toInt()
//                var idChat = it?.substringAfter(",").toString().toInt()
//
//                var novoDocId = docId.plus(1)
//                var novoIdChat = idChat.plus(1)
//                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
//                binding.editMessage.setText("$novoDocId e $novoIdChat")
//            }
        }
    }


    fun getList() {
        lifecycleScope.launch {
            var lista: MutableList<Chat> = mutableListOf()

            db.collection("messages").orderBy("id", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
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


    private fun recoverId(callback: (String?) -> Unit) {
        var lastFieldPath: String? = null
        var lastId: String? = null
        db.collection("messages").orderBy("id", Query.Direction.DESCENDING).limit(1)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    callback(null)
                    return@EventListener
                }

                value?.forEach {
                    lastId = it.get("id").toString()
                    lastFieldPath = it.reference.id
                }

                callback("$lastId,$lastFieldPath")
            })
    }

    fun enviarMensagem() {
        getList()
        recoverId {
            var docId = it?.substringBefore(",").toString().toInt()
            var idChat = it?.substringAfter(",").toString().toInt()

            var novoDocId = docId.plus(1)
            var novoIdChat = idChat.plus(1)
        lifecycleScope.launch {
            val chatCollection = db.collection("messages").document("$novoDocId")
            var message = binding.editMessage.text.toString()

            var messageMap = hashMapOf(
                "id" to novoIdChat,
                "message" to message
            )

            chatCollection.set(messageMap)
            Toast.makeText(this@UserActivity, novoDocId.toString() , Toast.LENGTH_SHORT).show()
        }
        }
        }
}
