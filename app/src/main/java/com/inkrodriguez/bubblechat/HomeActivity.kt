package com.inkrodriguez.bubblechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
        supportActionBar?.hide()

        var intent = intent.getStringExtra("username")
        var usuarioAtual = intent.toString()

        viewModel.vmBinding = this.binding

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnFeed.setOnClickListener {
            navController.navigate(R.id.feedFragment)
            viewModel.selectButton = 1
            viewModel.setImage()
        }

        binding.btnSearch.setOnClickListener {
            navController.navigate(R.id.searchFragment)
            viewModel.selectButton = 2
            viewModel.setImage()
        }

        binding.btnAddPublication.setOnClickListener {
            navController.navigate(R.id.conversasFragment)
            viewModel.selectButton = 3
            viewModel.setImage()
        }

        binding.btnContacts.setOnClickListener {
            navController.navigate(R.id.contatosFragment)
            viewModel.selectButton = 4
            viewModel.setImage()
        }

        binding.btnSettings.setOnClickListener {
            navController.navigate(R.id.perfilFragment)
            viewModel.selectButton = 5
            viewModel.setImage()
        }

    }
}