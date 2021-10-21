package com.stevehechio.apps.mziiketrailers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpViews()
    }

    private fun setUpViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment?
        val  navController: NavController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(binding.navigation, navController)
    }

}