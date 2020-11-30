package com.codexo.tictactoe

interface DataStoreInterface {

    fun getSoundStatus(): Boolean
    fun getGamesPlayed(): Int
    fun setGamesPlayed()
    fun setDayNight(switch: Boolean)
}