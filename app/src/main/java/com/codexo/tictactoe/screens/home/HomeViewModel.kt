package com.codexo.tictactoe.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel Destroyed!")
    }
}