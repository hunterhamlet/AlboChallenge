package com.hamon.albochallengenormal.features.exercises_practice_code.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hamon.albochallengenormal.binding.CharacterItem
import com.hamon.albochallengenormal.databinding.ActivityMainBinding
import com.hamon.albochallengenormal.features.exercises_practice_code.presentation.view_models.MainViewModel
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModel<MainViewModel>()
    private val adapter by lazy { GroupieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        setupObservables()
        setupRequest()
        binding.customView.setupCounter(15, 25)
    }

    private fun setupRequest() {
        mainViewModel.apply {
            getCharacters()
            getAwaitCharacters()
            getDeferredCharacters()
        }
    }

    private fun setupObservables() {
        mainViewModel.apply {
            characterObserver.observe(this@MainActivity) {
                adapter.clear()
                adapter.addAll(it.map { character ->
                    CharacterItem(character)
                })
            }
            characterAwaitObserver.observe(this@MainActivity) {
                Log.d("JHMM", "charactersAwaitObservable size: ${it.size}")
            }
            characterDeferredObserver.observe(this@MainActivity) {
                Log.d("JHMM", "charactersDeferredObservable size: ${it.size}")
            }
        }
    }

    private fun setupRecyclerView() {
        binding.characterList.adapter = adapter
    }

}