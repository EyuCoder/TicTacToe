package com.codexo.tictactoe.screens

import android.util.Log
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var emptyCells = ArrayList<Int>()
    var xoTable = arrayOf<Char>()
    var turn = '-'
    var freeSpots = 0
    var xScore = 0
    var oScore = 0

    init {
        //Log.i("SinglePlayerViewModel", "ViewModel created!")
    }

    fun clearGame() {
        emptyCells.clear()
        xoTable = emptyArray()
        turn = '-'
        freeSpots = 0
    }

    fun initGame() {
        for (i in 0..8) {
            xoTable += '-'
        }
        turn = 'x'
        freeSpots = 9
    }

    fun checkWinner(): Char? {
        val symbol = arrayOf('x', 'o')
        val draw ='-'
        for (j in symbol.indices) {

            // Check for row
            if (xoTable[0] == symbol[j] && xoTable[1] == symbol[j] && xoTable[2] == symbol[j]) return symbol[j]
            if (xoTable[3] == symbol[j] && xoTable[4] == symbol[j] && xoTable[5] == symbol[j]) return symbol[j]
            if (xoTable[6] == symbol[j] && xoTable[7] == symbol[j] && xoTable[8] == symbol[j]) return symbol[j]

            // Check for col
            if (xoTable[0] == symbol[j] && xoTable[3] == symbol[j] && xoTable[6] == symbol[j]) return symbol[j]
            if (xoTable[1] == symbol[j] && xoTable[4] == symbol[j] && xoTable[7] == symbol[j]) return symbol[j]
            if (xoTable[2] == symbol[j] && xoTable[5] == symbol[j] && xoTable[8] == symbol[j]) return symbol[j]

            // Check for diagonal
            if (xoTable[0] == symbol[j] && xoTable[4] == symbol[j] && xoTable[8] == symbol[j]) return symbol[j]
            if (xoTable[2] == symbol[j] && xoTable[4] == symbol[j] && xoTable[6] == symbol[j]) return symbol[j]
        }

        Log.i("CheckWinner", "$freeSpots Empty space available!")
        if (freeSpots==0) return draw
        return null
    }

    fun setLeftMoves(){
        emptyCells.clear()
        for (cellID in 0..8) {
            if (xoTable[cellID]=='-') {
                emptyCells.add(cellID)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("SinglePlayerViewModel", "SinglePlayerViewModel Destroyed!")
    }
}