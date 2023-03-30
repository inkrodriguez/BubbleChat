package com.inkrodriguez.bubblechat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var connect = Firebase.firestore
    private var db = connect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnEntrar.setOnClickListener {

            var editLogin = binding.editLogin.text.toString()
            var editPassword = binding.editPassword.text.toString()

            if (editLogin.isEmpty() || editPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                db.collection("users").document(editLogin).addSnapshotListener { value, error ->
                    if (value != null) {
                        var username = value.getString("username")
                        var password = value.getString("password")
                        if (editPassword == password) {
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                            Toast.makeText(
                                this,
                                "tudo certo, logado! $editLogin/$editPassword e $username/$password",
                                Toast.LENGTH_SHORT
                            ).show()
                            val sharedPref = this.applicationContext.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("USERNAME", username)
                            editor.apply()

                        } else {
                            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }



    fun getPreferences(){
        val sharedPref = this.applicationContext.getSharedPreferences("nome_da_preferencia", Context.MODE_PRIVATE)


    }

}