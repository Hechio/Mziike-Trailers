package com.stevehechio.apps.mziiketrailers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stevehechio.apps.mziiketrailers.databinding.ActivitySearchMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {

    }
}