package com.codexo.tictactoe

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataHandler(context: Context) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")

    //Create keys
    companion object {
        val GAME_SOUND = preferencesKey<Boolean>("GAME_SOUND")
        val GAME_TOTAL = preferencesKey<Int>("GAME_TOTAL")
        val DAYNIGHT = preferencesKey<Int>("DAYNIGHT")
    }

    suspend fun setSound(sound: Boolean){
        dataStore.edit {
            it[GAME_SOUND] = sound
        }
    }

    suspend fun setGamesPlayed(total: Int){
        dataStore.edit {
            it[GAME_TOTAL] = total
        }
    }

    //get a sound switch flow
    val gameSoundFlow: Flow<Boolean> = dataStore.data.map {
        it[GAME_SOUND] ?: true
    }

    //get totalGames played flow
    val gameTotalFlow: Flow<Int> = dataStore.data.map {
        it[GAME_TOTAL] ?: 0
    }

    //get dayNight toggle flow
    val dayNightFlow: Flow<Boolean> = dataStore.data.map {
        it[GAME_SOUND] ?: true
    }
}