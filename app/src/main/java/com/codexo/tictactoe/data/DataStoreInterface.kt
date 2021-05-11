package com.codexo.tictactoe.data

interface DataStoreInterface {

    fun getSoundStatus(): Boolean
    fun getGamesPlayed(): Int
    fun setGamesPlayed()
    fun setDayNight(switch: Boolean)
}