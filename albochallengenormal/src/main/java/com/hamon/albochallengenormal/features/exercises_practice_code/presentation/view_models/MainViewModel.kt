package com.hamon.albochallengenormal.features.exercises_practice_code.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.entities.RickAndMortyCharacter
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterDeferredUseCase
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterDeferredUseCase: GetCharacterDeferredUseCase
) : ViewModel() {

    // Normal

    private val _characterObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver

    // Deferred
    private val _characterDeferredObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterDeferredObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver

    // Await
    private val _characterAwaitObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterAwaitObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver


    fun getCharacters() {
        Log.d("JHMM", "getCharacters")
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCharacterUseCase.invoke()
            _characterObserver.postValue(result)
        }
    }

    fun getAwaitCharacters() {
        Log.d("JHMM", "getAwaitCharacters")
        viewModelScope.launch(Dispatchers.IO) {
            val resultAsync = async { getCharacterUseCase.invoke() }
            val result = resultAsync.await()
            _characterAwaitObserver.postValue(result)
        }
    }

    fun getDeferredCharacters() {
        Log.d("JHMM", "getDeferredCharacters")
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCharacterDeferredUseCase.invoke()
            _characterDeferredObserver.postValue(result)
        }
    }


}