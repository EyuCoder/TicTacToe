package com.codexo.tictactoe

interface DataStoreInterface {

    fun getSoundStatus(): Boolean

    fun setSoundStatus(switch: Boolean)
    fun setGamesPlayed(total: Int)
    fun setGameWins(wins: Int)
    fun setDayNight(switch: Boolean)
    fun setData(sound: Boolean, total: Int, wins: Int)
}