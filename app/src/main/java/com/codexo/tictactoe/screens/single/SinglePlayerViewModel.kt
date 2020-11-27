package com.codexo.tictactoe.screens.single

import android.util.Log
import androidx.lifecycle.ViewModel

class SinglePlayerViewModel : ViewModel() {
    var emptyCells = ArrayList<Int>()
    var turn = '-'
    var xoTable = arrayOf<Char>()

    init {
        Log.i("SinglePlayerViewModel", "SinglePlayerViewModel created!")
    }

    fun clearGame() {
        emptyCells.clear()
        xoTable = emptyArray()
        turn = '-'
    }

    fun initGame() {
        for (i in 0..8) {
            xoTable += '-'
        }
        turn = 'x'
    }

    fun checkWinner(): Char? {
        val symbol = arrayOf('x', 'o')
        val draw = '-'

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
        if (!xoTable.any{ it == '-'}) return '-'
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