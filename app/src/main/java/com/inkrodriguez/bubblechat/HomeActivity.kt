package com.inkrodriguez.bubblechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.inkrodriguez.bubblechat.data.Connect
import com.inkrodriguez.bubblechat.data.MyAdapter
import com.inkrodriguez.bubblechat.data.User
import com.inkrodriguez.bubblechat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnContatos.setOnClickListener {
            navController.navigate(R.id.contatos)
        }

        binding.btnMessages.setOnClickListener {
            navController.navigate(R.id.conversas)
        }

        binding.btnStatus.setOnClickListener {
            navController.navigate(R.id.status)
        }

    }
}