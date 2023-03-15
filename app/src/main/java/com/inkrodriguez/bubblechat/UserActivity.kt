package com.inkrodriguez.bubblechat

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.data.*
import com.inkrodriguez.bubblechat.databinding.ActivityUserBinding
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
            recoverId {
                var docId = it?.substringBefore(",")
                var idChat = it?.substringAfter(",")
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                binding.editMessage.setText("$docId e $idChat")
            }
        }
    }


    fun getList() {
        lifecycleScope.launch {
            var lista: MutableList<Chat> = mutableListOf()

            db.collection("messages").orderBy(FieldPath.documentId(), Query.Direction.ASCENDING).addSnapshotListener { value, error ->
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

                for (doc in value!!) {
                    lastId = doc.get("id").toString()
                    lastFieldPath = doc.reference.id
                }

                callback("$lastId,$lastFieldPath")
            })
    }




    fun enviarMensagem() {
        lifecycleScope.launch {
            val chatCollection = db.collection("messages")
            var message = binding.editMessage.text.toString()

            var messageMap = hashMapOf(
                "message" to message
            )

            val newDoc = chatCollection.add(messageMap)

            newDoc.addOnCompleteListener {
                Log.d(TAG, it.result.id)
            }

//            newDoc.addOnSuccessListener {
//                //ID DO FIELDPATCH
//                var getFieldPathId = it.id
//
//                var getDocId = it.get().result.getString("id")
//
//                Toast.makeText(this@UserActivity, "$getFieldPathId e $getDocId", Toast.LENGTH_SHORT).show()
//
//            }



//            newDoc.addOnSuccessListener {
//                val documentId = it.id + 1
//                Toast.makeText(this@UserActivity, documentId, Toast.LENGTH_SHORT).show()
//                chatCollection.document(documentId).update(FieldPath.documentId(), documentId).addOnCompleteListener {
//                    getList()
//                    Toast.makeText(this@UserActivity, "os Dados foram atualizados", Toast.LENGTH_SHORT).show()
//                }
//
//            }

//            //Adiciona um novo documento
//            newDoc.addOnCompleteListener {
//                //colocar um som ao enviar a mensagem
//                Toast.makeText(this@UserActivity, "tudo certo $it", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this@UserActivity, "deu erro", Toast.LENGTH_SHORT).show()
//            }

//
//                db.collection("messages").document("3").set(messageMap).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        Toast.makeText(this@UserActivity, "tudo ok", Toast.LENGTH_SHORT).show()
//                    }
//                }.addOnFailureListener {
//                }
        }
        }
}
