package com.inkrodriguez.bubblechat

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.databinding.ActivitySettingsBinding
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private var connect = Firebase.firestore
    private var db = connect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPref: SharedPreferences? = applicationContext.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()

        lifecycleScope.launch {
            readData(sharedPrefencesValue, binding.editFullname, binding.editUsername, binding.editOcupation, binding.editBiography)
        }
    }

    private fun readData(sharedPrefencesValue: String, editFullname: EditText, editUsername: EditText, editOcupation: EditText, editBiography: EditText){
        db.collection("users").document(sharedPrefencesValue).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            } else {

                val fullname = value?.get("fullname").toString()
                val username = value?.get("username").toString()
                val ocupation = value?.get("ocupation").toString()
                val biography = value?.get("biography").toString()

                editFullname.setText(fullname)
                editUsername.setText(username)
                editOcupation.setText(ocupation)
                editBiography.setText(biography)

            }
        }
    }

}