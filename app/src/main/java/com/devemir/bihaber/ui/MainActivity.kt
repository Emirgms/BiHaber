package com.devemir.bihaber.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.devemir.bihaber.R
import com.devemir.bihaber.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setNavBar()

    }
    private fun setNavBar () {
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.news -> {
                navController.navigate(R.id.newsFragment)
                    true
                }
                R.id.search -> {
                    navController.navigate(R.id.searchViewFragment)
                    true
                }
                else -> {
                    navController.navigate(R.id.bookmarksFragment)
                    true
                }
            }
        }
    }
}