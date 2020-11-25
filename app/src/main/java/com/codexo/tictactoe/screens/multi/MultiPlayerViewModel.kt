package com.codexo.tictactoe.screens.multi

import android.util.Log
import androidx.lifecycle.ViewModel

class MultiPlayerViewModel: ViewModel() {
    init {
        Log.i("MultiPlayerViewModel", "MultiPlayerViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MultiPlayerViewModel", "MultiPlayerViewModel Destroyed!")
    }
}