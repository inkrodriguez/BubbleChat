package com.inkrodriguez.bubblechat.data

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val db = Firebase.firestore

    fun getSharedPreferences(context: Context): String {
        val sharedPref: SharedPreferences? = context.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        return sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()
    }

        // função para obter informações do usuário
        fun getUserInfo(username: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
            val userRef = db.collection("users").document(username)
            userRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = User(
                            username = document.getString("username").orEmpty(),
                            fotoperfil = document.getString("fotoPerfil").orEmpty(),
                            ocupation = document.getString("ocupation").orEmpty()
                        )
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User not found"))
                    }
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        }
    }
