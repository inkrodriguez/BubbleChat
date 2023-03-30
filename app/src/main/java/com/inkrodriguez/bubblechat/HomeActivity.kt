package com.inkrodriguez.bubblechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

class HomeActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContentView(binding.root)

        var intent = intent.getStringExtra("username")
        var usuarioAtual = intent.toString()

        viewModel.vmBinding = this.binding

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnContatos.setOnClickListener {
            navController.navigate(R.id.contatos)
            viewModel.selectButton = 1
            viewModel.setImage()
        }

        binding.btnMessages.setOnClickListener {
            navController.navigate(R.id.conversas)
            viewModel.selectButton = 2
            viewModel.setImage()
        }

        binding.btnStatus.setOnClickListener {
            navController.navigate(R.id.status)
            viewModel.selectButton = 3
            viewModel.setImage()
        }

    }
}