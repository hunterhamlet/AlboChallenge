package com.hamon.albochallengenormal.features.exercises_practice_code.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamon.albochallengenormal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}